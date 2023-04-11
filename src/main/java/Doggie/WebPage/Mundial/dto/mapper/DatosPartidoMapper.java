package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.*;
import Doggie.WebPage.Mundial.modelo.entidad.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")

public interface DatosPartidoMapper {

    default DatosPartido from(List<Plantilla> plantillas, List<MarcadorEquipo> marcador, List<GolesEquipo> equiposGoles, Partido partido, List<DatosEstadistica> estadisticas, List<MarcadorEquipo> marcadorPenales, List<Penales> penales) {

        var datosArbitro = getDatosArbitro(partido.getArbitro());
        var fase = partido.getFase().getNombre();

        return new DatosPartido(
                plantillas,
                marcador,
                equiposGoles,
                partido.getFecha(),
                partido.getHora(),
                partido.getEstadio().getNombre(),
                datosArbitro,
                fase,
                estadisticas,
                partido.isTandaPenales() ? marcadorPenales : null,
                partido.isTandaPenales() ? penales : null
        );
    }

    private DatosArbitro getDatosArbitro(Arbitro arbitro) {
        return new DatosArbitro(arbitro.getNombre(), arbitro.getPais().getNombre());
    }
}
