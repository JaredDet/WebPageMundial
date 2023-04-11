package Doggie.WebPage.Mundial.servicio;

import Doggie.WebPage.Mundial.modelo.entidad.Jugador;
import Doggie.WebPage.Mundial.modelo.repositorio.RepositorioJugador;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class JugadorCache {
    private final LoadingCache<CacheKey, List<Jugador>> cache;
    private final RepositorioJugador repositorioJugador;

    @Autowired
    public JugadorCache(RepositorioJugador repositorioJugador) {
        this.repositorioJugador = repositorioJugador;
        this.cache = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(key -> {
                    var equipoId = key.getEquipoId();
                    var partidoId = key.getPartidoId();
                    return this.repositorioJugador.findJugadoresConvocadosByEquipoYPartido(equipoId, partidoId);
                });
    }

    public List<Jugador> getJugadoresConvocados(long equipoId, long partidoId) {
        CacheKey key = new CacheKey(equipoId, partidoId);
        return cache.get(key);
    }

    public List<Jugador> getJugadoresConGoles(long equipoId, long partidoId) {
        CacheKey key = new CacheKey(equipoId, partidoId);
        return cache.get(key, k -> repositorioJugador.findJugadoresGolesByEquipoYPartido(k.equipoId, k.partidoId));
    }

    private static class CacheKey {
        private final long equipoId;
        private final long partidoId;

        public CacheKey(long equipoId, long partidoId) {
            this.equipoId = equipoId;
            this.partidoId = partidoId;
        }

        public long getEquipoId() {
            return equipoId;
        }

        public long getPartidoId() {
            return partidoId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            var cacheKey = (CacheKey) o;
            return equipoId == cacheKey.equipoId && partidoId == cacheKey.partidoId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(equipoId, partidoId);
        }
    }
}