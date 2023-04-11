package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.*;
import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RondaMapper {

    MarcadorMapper marcadorMapper = new MarcadorMapperImpl();

    default Ronda from(Partido partido) {
        var marcador = marcadorMapper.marcadorGolesFrom(partido);
        var marcadorPenales = marcadorMapper.marcadorPenalesFrom(partido);
        var fase = getFase(partido);

        return new Ronda(marcador, marcadorPenales, fase, partido.getFecha());
    }

    private String getFase(Partido partido) {
        var fase = partido.getFase().getNombre();
        if ("Fase de grupos".equalsIgnoreCase(fase)) {
            return "Grupo " + partido.getEquiposParticipantes().get(0).getEquipo().getGrupo().getNombre();
        }
        return fase;
    }

    default List<Ronda> from(List<Partido> partidos) {
        return partidos.stream().map(this::from).toList();
    }
}
