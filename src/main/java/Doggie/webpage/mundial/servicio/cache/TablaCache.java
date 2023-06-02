package Doggie.WebPage.Mundial.servicio.cache;

import Doggie.WebPage.Mundial.excepciones.PartidosNoEncontradosPorGrupoException;
import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import Doggie.WebPage.Mundial.modelo.repositorio.RepositorioPartido;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Component
@Slf4j
public class TablaCache {
    private final LoadingCache<Long, List<Partido>> cache;

    @Autowired
    public TablaCache(RepositorioPartido repositorioPartido) {

        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(100)
                .build(grupoId ->
                        grupoId == 0L ? repositorioPartido.findFaseGrupos() : repositorioPartido.findByGrupoId(grupoId));
    }

    public List<Partido> getPartidosByGrupoId(Long grupoId) {

        return Optional.ofNullable(cache.get(grupoId))
                .orElseThrow(() -> new PartidosNoEncontradosPorGrupoException(grupoId));
    }

    public List<Partido> getFaseGrupos() {
        return Optional.ofNullable(cache.get(0L))
                .orElseThrow(() -> new PartidosNoEncontradosPorGrupoException(0L));
    }
}


