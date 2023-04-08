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

    private final RepositorioPartido repositorioPartido;
    private final RondaMapper datosMarcadorMapper;

    public List<Ronda> findByFase(Long faseId) {
        var partidos = repositorioPartido.findByFaseId(faseId);
        return datosMarcadorMapper.from(partidos);
    }

    public List<Ronda> findRondaFinal() {
        var partidos = repositorioPartido.findRondaFinal();
        return datosMarcadorMapper.from(partidos);
    }
}
