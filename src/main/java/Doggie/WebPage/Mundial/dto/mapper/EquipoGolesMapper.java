package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.EquipoGoles;
import Doggie.WebPage.Mundial.modelo.entidad.Equipo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = JugadorGolesMapper.class)

public interface EquipoGolesMapper {

    @Mapping(target = "goles", source = "jugadores")
    EquipoGoles from(Equipo equipo);
}
