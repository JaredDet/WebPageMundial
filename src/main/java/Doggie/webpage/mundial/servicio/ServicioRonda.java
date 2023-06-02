package Doggie.WebPage.Mundial.servicio;

import Doggie.WebPage.Mundial.dto.ResumenPartido;
import Doggie.WebPage.Mundial.dto.mapper.RondaMapper;
import Doggie.WebPage.Mundial.servicio.cache.RondaCache;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicioRonda {

    private final RondaCache rondaCache;
    private final RondaMapper rondaMapper;

    /**
     * Obtiene una lista de resúmenes de partidos correspondientes a una fase especificada.
     *
     * @param faseId el ID de la fase de la que se desean obtener los resúmenes de partidos.
     * @return una lista de resúmenes de partidos correspondientes a la fase especificada.
     */

    @Transactional
    public List<ResumenPartido> findByFase(Long faseId) {
        var partidos = rondaCache.getPartidosByFaseId(faseId);
        return rondaMapper.from(partidos);
    }

    /**
     * Obtiene una lista de resúmenes de partidos correspondientes a la fase final del mundial.
     *
     * @return una lista de resúmenes de todos los partidos desde octavos de final hasta la final.
     */

    @Transactional
    public List<ResumenPartido> findRondaFinal() {
        var partidos = rondaCache.getRondaFinal();
        return rondaMapper.from(partidos);
    }
}
