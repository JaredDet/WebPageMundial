package Doggie.WebPage.Mundial.servicio;

import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import Doggie.WebPage.Mundial.modelo.repositorio.RepositorioPartido;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RondaCache {

    private final LoadingCache<Long, List<Partido>> cache;

    @Autowired
    public RondaCache(RepositorioPartido repositorioPartido) {

        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(100)
                .build(faseId -> {
                    var partidos = faseId == 0L ? repositorioPartido.findRondaFinal() : repositorioPartido.findByFaseId(faseId);
                    if (partidos != null) {
                        partidos.forEach(partido -> {
                            Hibernate.initialize(partido.getGoles());
                            Hibernate.initialize(partido.getEquiposParticipantes());
                        });
                    }

                    return partidos;
                });
    }

    public List<Partido> getPartidosByFaseId(Long faseId) {
        return cache.get(faseId);
    }

    public List<Partido> getRondaFinal() {
        return cache.get(0L);
    }
}
