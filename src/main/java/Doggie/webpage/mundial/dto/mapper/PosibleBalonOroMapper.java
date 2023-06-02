package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.PosibleBalonOro;
import Doggie.WebPage.Mundial.modelo.entidad.Jugador;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface PosibleBalonOroMapper {
    default PosibleBalonOro from(Jugador jugador) {
        var nombre = jugador.getNombre();
        var mvps = jugador.getVecesJugadorPartido();
        return new PosibleBalonOro(nombre, mvps);
    }
}
