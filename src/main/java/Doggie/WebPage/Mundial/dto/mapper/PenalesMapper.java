package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.PenalJugador;
import Doggie.WebPage.Mundial.dto.Penales;
import Doggie.WebPage.Mundial.modelo.entidad.Equipo;
import Doggie.WebPage.Mundial.modelo.entidad.Jugador;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface PenalesMapper {

    default Penales from(Equipo equipo) {

        return new Penales(
                equipo.getJugadores().stream()
                        .filter(jugador -> !jugador.getGoles().isEmpty())
                        .map(this::from)
                        .toList());
    }

    default PenalJugador from(Jugador jugador) {
        var gol = jugador.getGoles().get(0);
        return new PenalJugador(jugador.getNombre(), gol.isEntro());
    }
}
