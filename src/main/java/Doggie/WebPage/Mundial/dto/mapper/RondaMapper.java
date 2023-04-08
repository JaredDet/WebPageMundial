package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.*;
import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RondaMapper {

    MarcadorMapper marcadorMapper = new MarcadorMapperImpl();

    default Ronda from(Partido partido) {
        var marcador = marcadorMapper.from(partido);
        return new Ronda(marcador, partido.getFase().getNombre(), partido.getFecha());
    }

    default List<Ronda> from(List<Partido> partidos) {
        return partidos.stream().map(this::from).toList();
    }
}
