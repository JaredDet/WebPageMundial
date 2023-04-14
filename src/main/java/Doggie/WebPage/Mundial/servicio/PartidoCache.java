package Doggie.WebPage.Mundial.servicio;

import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import Doggie.WebPage.Mundial.modelo.repositorio.RepositorioPartido;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class PartidoCache {
    private final LoadingCache<Long, Partido> cache;

    @Autowired
    public PartidoCache(RepositorioPartido repositorioPartido) {

        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(100)
                .build(partidoId -> {
                    Partido partido = repositorioPartido.findById(partidoId).orElse(null);
                    if (partido != null) {
                        Hibernate.initialize(partido.getEquiposParticipantes());
                        Hibernate.initialize(partido.getGoles());
                        partido.getEquiposParticipantes().forEach(equipo -> {
                            Hibernate.initialize(equipo.getEquipo().getJugadores());
                            Hibernate.initialize(equipo.getEquipo().getPartidosParticipados());
                        });
                    }
                    return partido;
                });
    }

    public Partido getPartidoById(Long partidoId) {
        return cache.get(partidoId);
    }
}


