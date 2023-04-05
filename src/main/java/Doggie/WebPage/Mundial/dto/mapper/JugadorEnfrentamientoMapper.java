package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.JugadorEnfrentamiento;
import Doggie.WebPage.Mundial.modelo.entidad.Jugador;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")


public interface JugadorEnfrentamientoMapper {

    JugadorEnfrentamiento from(Jugador jugador);
}
