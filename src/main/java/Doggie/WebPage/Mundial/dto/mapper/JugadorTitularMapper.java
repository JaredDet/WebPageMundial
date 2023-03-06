package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.JugadorTitular;
import Doggie.WebPage.Mundial.modelo.entidad.Jugador;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JugadorTitularMapper {

    @Mapping(target = "posicion", source = "posicionTitular.nombre")
    JugadorTitular toJugadorTitular(Jugador jugador);
}