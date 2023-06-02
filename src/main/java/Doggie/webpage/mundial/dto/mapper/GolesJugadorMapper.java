package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.DatosGol;
import Doggie.WebPage.Mundial.dto.GolesJugador;
import Doggie.WebPage.Mundial.modelo.entidad.Gol;
import Doggie.WebPage.Mundial.modelo.entidad.Jugador;
import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")

public interface GolesJugadorMapper {

    default List<GolesJugador> from(List<Jugador> jugadores, Partido partido) {
        return jugadores.stream().map(jugador -> from(jugador, partido)).toList();
    }

    default GolesJugador from(Jugador jugador, Partido partido) {
        var goles = jugador.getGolesPorPartido(partido);
        return new GolesJugador(jugador.getNombre(), from(goles));
    }

    default List<DatosGol> from(List<Gol> goles) {
        return goles.stream()
                .filter(Gol::esGolReglamentario)
                .map(gol -> new DatosGol(gol.getMinuto(), gol.isPenal()))
                .toList();
    }
}
