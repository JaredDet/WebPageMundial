package Doggie.WebPage.Mundial.servicio.cache;

import Doggie.WebPage.Mundial.excepciones.PartidosNoEncontradosPorGrupoException;
import Doggie.WebPage.Mundial.modelo.DetallesPartido;
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
    private final CargaPartido cargaPartido;


    @Autowired
    public TablaCache(RepositorioPartido repositorioPartido, CargaPartido cargaPartido) {

        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(100)
                .build(grupoId ->
                        grupoId == 0L ? repositorioPartido.findFaseGrupos() : repositorioPartido.findByGrupoId(grupoId));
        this.cargaPartido = cargaPartido;
    }

    @Transactional(propagation = REQUIRES_NEW)
    public List<Partido> getPartidosByGrupoId(Long grupoId) {

        var partidos = Optional.ofNullable(cache.get(grupoId))
                .orElseThrow(() -> new PartidosNoEncontradosPorGrupoException(grupoId));

        cargaPartido.cargar(partidos, List.of(DetallesPartido.EQUIPOS, DetallesPartido.GOLES));
        return partidos;
    }

    @Transactional(propagation = REQUIRES_NEW)
    public List<Partido> getFaseGrupos() {
        var partidos = Optional.ofNullable(cache.get(0L))
                .orElseThrow(() -> new PartidosNoEncontradosPorGrupoException(0L));
        cargaPartido.cargar(partidos, List.of(DetallesPartido.EQUIPOS, DetallesPartido.GOLES));
        return partidos;
    }
}


