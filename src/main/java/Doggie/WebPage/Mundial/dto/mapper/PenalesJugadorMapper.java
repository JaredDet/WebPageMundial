package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.GolesJugador;
import Doggie.WebPage.Mundial.dto.PenalJugador;
import Doggie.WebPage.Mundial.modelo.entidad.Gol;
import Doggie.WebPage.Mundial.modelo.entidad.Jugador;
import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")

public interface PenalesJugadorMapper {

    default List<PenalJugador> from(List<Jugador> jugadores, Partido partido) {
        return jugadores.stream().map(jugador -> from(jugador, partido)).toList();
    }

    default PenalJugador from(Jugador jugador, Partido partido) {
        var goles = jugador.getGolesPorPartido(partido);

        var penal = goles.stream().filter(Gol::esGolTandaPenales).findFirst();

        return penal.map(gol -> new PenalJugador(jugador.getNombre(), gol.isEntro(), !gol.isEntro())).orElse(null);
    }
}
