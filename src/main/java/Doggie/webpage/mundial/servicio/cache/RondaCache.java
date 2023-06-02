package Doggie.WebPage.Mundial.servicio.cache;

import Doggie.WebPage.Mundial.excepciones.PartidosNoEncontradosPorGrupoException;
import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import Doggie.WebPage.Mundial.modelo.repositorio.RepositorioPartido;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
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

    @Autowired
    public RondaCache(RepositorioPartido repositorioPartido) {

        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(100)
                .build(faseId ->
                        faseId == 0L ? repositorioPartido.findRondaFinal() :
                                repositorioPartido.findByFaseId(faseId));
    }

    public List<Partido> getPartidosByFaseId(Long faseId) {
        return Optional.ofNullable(cache.get(faseId))
                .orElseThrow(() -> new PartidosNoEncontradosPorGrupoException(faseId));
    }

    public List<Partido> getRondaFinal() {
        return Optional.ofNullable(cache.get(0L))
                .orElseThrow(() -> new PartidosNoEncontradosPorGrupoException(0L));
    }
}
