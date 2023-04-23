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

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Component
public class RondaCache {

    private final LoadingCache<Long, List<Partido>> cache;
    private final CargaPartido cargaPartido;

    @Autowired
    public RondaCache(RepositorioPartido repositorioPartido, CargaPartido cargaPartido) {

        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(100)
                .build(faseId ->
                        faseId == 0L ? repositorioPartido.findRondaFinal() :
                                repositorioPartido.findByFaseId(faseId));
        this.cargaPartido = cargaPartido;
    }

    @Transactional(propagation = REQUIRES_NEW)
    public List<Partido> getPartidosByFaseId(Long faseId) {
        var partidos = Optional.ofNullable(cache.get(faseId))
                .orElseThrow(() -> new PartidosNoEncontradosPorGrupoException(faseId));
        cargaPartido.cargar(partidos, List.of(DetallesPartido.EQUIPOS, DetallesPartido.GOLES, DetallesPartido.FASE));
        return partidos;
    }

    @Transactional(propagation = REQUIRES_NEW)
    public List<Partido> getRondaFinal() {
        var partidos = Optional.ofNullable(cache.get(0L))
                .orElseThrow(() -> new PartidosNoEncontradosPorGrupoException(0L));
        cargaPartido.cargar(partidos, List.of(DetallesPartido.EQUIPOS, DetallesPartido.GOLES, DetallesPartido.FASE));
        return partidos;
    }
}
