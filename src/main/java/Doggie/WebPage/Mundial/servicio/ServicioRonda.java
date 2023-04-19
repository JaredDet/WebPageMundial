package Doggie.WebPage.Mundial.servicio;

import Doggie.WebPage.Mundial.dto.ResumenPartido;
import Doggie.WebPage.Mundial.dto.mapper.RondaMapper;
import Doggie.WebPage.Mundial.modelo.entidad.Partido;
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

    @Transactional
    public List<ResumenPartido> findByFase(Long faseId) {
        var partidos = rondaCache.getPartidosByFaseId(faseId);
        return rondaMapper.from(partidos);
    }

    @Transactional
    public List<ResumenPartido> findRondaFinal() {
        var partidos = rondaCache.getRondaFinal();
        return rondaMapper.from(partidos);
    }
}
