package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.*;
import Doggie.WebPage.Mundial.modelo.entidad.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")

public interface DatosPartidoMapper {

    default DatosPartido from(List<Plantilla> plantillas, List<MarcadorEquipo> marcador, List<GolesEquipo> equiposGoles, DatosExtrasPartido datosExtraPartido, List<DatosEstadistica> estadisticas, List<MarcadorEquipo> marcadorPenales, List<PenalesEquipo> penales, Partido partido) {


        return new DatosPartido(
                plantillas,
                marcador,
                equiposGoles,
                datosExtraPartido,
                estadisticas,
                partido.isTandaPenales() ? marcadorPenales : null,
                partido.isTandaPenales() ? penales : null
        );
    }
}
