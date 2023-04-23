package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.PenalesEquipo;
import Doggie.WebPage.Mundial.modelo.entidad.Participante;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring", uses = PenalesJugadorMapper.class)

public interface PenalesEquipoMapper {

    PenalesJugadorMapper penalesJugadorMapper = new PenalesJugadorMapperImpl();

    List<PenalesEquipo> from(List<Participante> equipos);

    default PenalesEquipo from(Participante equipo) {
        var goles = penalesJugadorMapper.from(equipo.getAnotadoresTandaPenales(), equipo.getPartido());
        return new PenalesEquipo(equipo.getNombre(), goles);
    }
}