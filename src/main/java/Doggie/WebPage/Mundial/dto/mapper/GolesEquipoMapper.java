package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.GolesEquipo;
import Doggie.WebPage.Mundial.modelo.entidad.Equipo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = GolesJugadorMapper.class)

public interface GolesEquipoMapper {

    @Mapping(target = "nombre", source = "pais.nombre")
    @Mapping(target = "goles", source = "jugadores")
    GolesEquipo from(Equipo equipo);
}
