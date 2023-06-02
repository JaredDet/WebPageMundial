package Doggie.WebPage.Mundial.servicio.cache;

import Doggie.WebPage.Mundial.excepciones.PartidoNoEncontradoException;
import Doggie.WebPage.Mundial.modelo.entidad.*;
import Doggie.WebPage.Mundial.modelo.repositorio.RepositorioPartido;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class PartidoCache {
    private final LoadingCache<Long, Partido> cache;

    @Autowired
    public PartidoCache(RepositorioPartido repositorioPartido) {
        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(100)
                .build(repositorioPartido::findPartidoByIDForPartido);
    }

    public Partido getPartido(Long partidoId) {

        return Optional.of(cache.get(partidoId))
                .orElseThrow(() -> new PartidoNoEncontradoException(partidoId));
    }
}


