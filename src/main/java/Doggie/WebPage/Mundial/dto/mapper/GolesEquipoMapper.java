package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.GolesEquipo;
import Doggie.WebPage.Mundial.modelo.entidad.Participante;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = GolesJugadorMapper.class)

public interface GolesEquipoMapper {

    GolesJugadorMapper golesJugadorMapper = new GolesJugadorMapperImpl();
    List<GolesEquipo> from(List<Participante> equipos);

    default GolesEquipo from(Participante equipo) {
        var goles = golesJugadorMapper.from(equipo.getAnotadores(), equipo.getPartido());
        return new GolesEquipo(equipo.getNombre(), goles);
    }
}
