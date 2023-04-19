package Doggie.WebPage.Mundial.servicio.cache;

import Doggie.WebPage.Mundial.excepciones.JugadoresNoEncontradosException;
import Doggie.WebPage.Mundial.modelo.DetallesJugador;
import Doggie.WebPage.Mundial.modelo.entidad.*;
import Doggie.WebPage.Mundial.modelo.repositorio.RepositorioJugador;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Slf4j
@Component
public class JugadorCache {
    private final LoadingCache<CacheKey, List<Jugador>> cache;
    private final RepositorioJugador repositorioJugador;
    private final CargaJugador cargaJugador;

    @Autowired
    public JugadorCache(RepositorioJugador repositorioJugador, CargaJugador cargaJugador) {
        this.repositorioJugador = repositorioJugador;

        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(100)
                .build(key -> {
                    var equipoId = key.equipoId();
                    var partidoId = key.partidoId();
                    return this.repositorioJugador.findJugadoresConvocadosByEquipoYPartido(equipoId, partidoId);
                });
        this.cargaJugador = cargaJugador;
    }

    @Transactional(propagation = REQUIRES_NEW)
    public List<Jugador> getJugadores(Long equipoId, Long partidoId) {
        CacheKey key = new CacheKey(equipoId, partidoId);
        var jugadores = Optional.ofNullable(cache.get(key))
                .orElseThrow(() -> {
                    var excepcion = new JugadoresNoEncontradosException(partidoId, equipoId);
                    log.error(excepcion.getMensajePersonalizado());
                    return excepcion;
                });
        cargaJugador.cargar(jugadores, List.of(DetallesJugador.CONVOCACIONES, DetallesJugador.SUSTITUCIONES, DetallesJugador.TARJETAS, DetallesJugador.GOLES));
        return jugadores;
    }

    private record CacheKey(long equipoId, long partidoId) {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            var cacheKey = (CacheKey) o;
            return equipoId == cacheKey.equipoId && partidoId == cacheKey.partidoId;
        }

    }
}