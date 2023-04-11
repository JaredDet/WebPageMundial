package Doggie.WebPage.Mundial.servicio;

import Doggie.WebPage.Mundial.dto.Ronda;
import Doggie.WebPage.Mundial.dto.mapper.RondaMapper;
import Doggie.WebPage.Mundial.modelo.repositorio.RepositorioPartido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicioRonda {

    private final RondaCache rondaCache;
    private final RondaMapper rondaMapper;

    public List<Ronda> findByFase(Long faseId) {
        var partidos = rondaCache.getPartidosByFaseId(faseId);
        return rondaMapper.from(partidos);
    }

    public List<Ronda> findRondaFinal() {
        var partidos = rondaCache.getRondaFinal();
        return rondaMapper.from(partidos);
    }
}
