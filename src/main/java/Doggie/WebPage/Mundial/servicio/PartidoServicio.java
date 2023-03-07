package Doggie.WebPage.Mundial.servicio;

import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import Doggie.WebPage.Mundial.modelo.repositorio.PartidoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartidoServicio {

    private final PartidoRepositorio partidoRepositorio;
    private final FaseServicio faseServicio;

    private final GrupoServicio grupoServicio;

    public List<Partido> findByNombreFaseAndNombreGrupo(String nombreFase, String nombreGrupo) {
        var fase = faseServicio.findByNombre(nombreFase);
        var grupo = grupoServicio.findGrupo(nombreGrupo);

        if (grupo == null) {
            return partidoRepositorio.findByFase(fase);
        }
        return partidoRepositorio.findByFaseAndGrupo(fase.getFaseId(), grupo.getGrupoId());
    }
}
