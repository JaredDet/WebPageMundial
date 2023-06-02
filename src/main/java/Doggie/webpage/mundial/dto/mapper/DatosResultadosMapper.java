package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.DatosResultado;
import Doggie.WebPage.Mundial.modelo.entidad.Equipo;
import Doggie.WebPage.Mundial.modelo.entidad.Participante;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")

public interface DatosResultadosMapper {

    default DatosResultado from(Participante partidoEquipo) {

        var fecha = partidoEquipo.fechaJugada();
        var resultado = partidoEquipo.resultado();
        return new DatosResultado(resultado, fecha);
    }

    default List<DatosResultado> from(Equipo equipo) {
        return equipo.getPartidosParticipados()
                .stream()
                .map(this::from)
                .sorted()
                .toList();
    }
}
