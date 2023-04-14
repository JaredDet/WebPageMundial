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
public class TablaCache {
    private final LoadingCache<Long, List<Partido>> cache;


    @Autowired
    public TablaCache(RepositorioPartido repositorioPartido) {

        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(100)
                .build(grupoId -> {
                    var partidos = grupoId == 0L ? repositorioPartido.findFaseGrupos() : repositorioPartido.findByGrupoId(grupoId);

                    if (partidos != null) {
                        partidos.forEach(partido -> Hibernate.initialize(partido.getEquiposParticipantes()));
                    }

                    return partidos;
                });
    }

    public List<Partido> getPartidosByGrupoId(Long grupoId) {
        return cache.get(grupoId);
    }

    public List<Partido> getFaseGrupos() {
        return cache.get(0L);
    }
}


