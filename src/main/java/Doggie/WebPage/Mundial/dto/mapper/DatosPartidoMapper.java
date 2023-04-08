package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.*;
import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")

public interface DatosPartidoMapper {

    default DatosPartido from(List<Plantilla> plantillas, List<MarcadorEquipo> marcador, List<GolesEquipo> equiposGoles, Partido partido, List<DatosEstadistica> estadisticas) {

        var fecha = partido.getFecha();
        var hora = partido.getHora();
        var arbitro = partido.getArbitro();
        var datosArbitro = new DatosArbitro(arbitro.getNombre(), arbitro.getPais().getNombre());
        var estadio = partido.getEstadio().getNombre();
        var fase = partido.getFase().getNombre();

        return new DatosPartido(plantillas, marcador, equiposGoles, fecha, hora, estadio, datosArbitro, fase, estadisticas);
    }
}
