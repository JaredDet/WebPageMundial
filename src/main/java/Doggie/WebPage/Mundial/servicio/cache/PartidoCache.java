package Doggie.WebPage.Mundial.servicio.cache;

import Doggie.WebPage.Mundial.excepciones.PartidoNoEncontradoException;
import Doggie.WebPage.Mundial.modelo.DetallesPartido;
import Doggie.WebPage.Mundial.modelo.entidad.*;
import Doggie.WebPage.Mundial.modelo.repositorio.RepositorioPartido;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Component
@Slf4j
public class PartidoCache {
    private final LoadingCache<Long, Partido> cache;
    private final CargaPartido cargaPartido;

    @Autowired
    public PartidoCache(RepositorioPartido repositorioPartido, CargaPartido cargaPartido) {
        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(100)
                .build(partidoId -> repositorioPartido.findById(partidoId).orElse(null));
        this.cargaPartido = cargaPartido;
    }

    /**
     * Carga un partido y sus detalles especificados. Los detalles se especifican mediante una lista de valores
     * de la enumeración DetallesPartido.
     *
     * @param partidoId       el id del partido a cargar
     * @param detallesPartido la lista de detalles del partido a cargar
     * @return el partido con los detalles especificados
     * @throws PartidoNoEncontradoException si el partido con el id especificado no se encuentra en la caché
     */

    @Transactional(propagation = REQUIRES_NEW)
    public Partido getPartido(Long partidoId, List<DetallesPartido> detallesPartido) {

        var partido = Optional.ofNullable(cache.get(partidoId))
                .orElseThrow(() -> {
                    var excepcion = new PartidoNoEncontradoException(partidoId);
                    log.error(excepcion.getMensajePersonalizado());
                    return excepcion;
                });
        cargaPartido.cargar(partido, detallesPartido);
        return partido;
    }
}


