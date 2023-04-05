package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.EquipoEnfrentamiento;
import Doggie.WebPage.Mundial.modelo.entidad.Participante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = JugadorEnfrentamientoMapper.class)
public interface EquipoEnfrentamientoMapper {

    @Mapping(target = "jugadores", source = "equipo.jugadores")
    @Mapping(target = "nombre", source = "equipo.nombre")
    EquipoEnfrentamiento from(Participante participante);
}
