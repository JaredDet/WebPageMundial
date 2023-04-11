package Doggie.WebPage.Mundial.servicio;

import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import Doggie.WebPage.Mundial.modelo.repositorio.RepositorioPartido;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class PartidoCache {
    private final LoadingCache<Long, Partido> cache;
    private final RepositorioPartido repositorioPartido;

    @Autowired
    public PartidoCache(RepositorioPartido repositorioPartido) {
        this.repositorioPartido = repositorioPartido;

        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(100)
                .build(partidoId -> this.repositorioPartido.findById(partidoId).orElse(null));
    }

    public Partido getPartidoById(Long partidoId) {
        return cache.get(partidoId);
    }
}
