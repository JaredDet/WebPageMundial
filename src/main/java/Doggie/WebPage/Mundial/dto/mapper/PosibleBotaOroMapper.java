package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.PosibleBotaOro;
import Doggie.WebPage.Mundial.modelo.entidad.Jugador;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface PosibleBotaOroMapper {
    default PosibleBotaOro from(Jugador jugador) {
        var nombre = jugador.getNombre();
        var goles = jugador.getGoles().size();
        return new PosibleBotaOro(nombre, goles);
    }
}
