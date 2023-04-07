package Doggie.WebPage.Mundial.servicio;

import Doggie.WebPage.Mundial.modelo.entidad.*;
import Doggie.WebPage.Mundial.modelo.repositorio.RepositorioJugador;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ServicioJugador {
    private final RepositorioJugador repositorioJugador;

    public List<Jugador> findByEquipoYPartido(Equipo equipo, Partido partido) {
        var jugadores = repositorioJugador.findJugadoresConvocadosByEquipoYPartido(equipo.getEquipoId(), partido.getPartidoId());
        return jugadores.stream()
                .map(jugador -> filterConvocadoByPartido(jugador, partido))
                .map(jugador -> filterCambioByPartido(jugador, partido))
                .map(jugador -> filterTarjetasByPartido(jugador, partido)).toList();
    }

    public List<Jugador> findGolesByEquipoYPartido(Equipo equipo, Partido partido) {
        var jugadores = repositorioJugador.findJugadoresGolesByEquipoYPartido(equipo.getEquipoId(), partido.getPartidoId());
        return jugadores.stream().map(jugador -> filterGolesByPartido(jugador, partido)).toList();
    }

    private Jugador filterGolesByPartido(Jugador jugador, Partido partido) {
        var goles = jugador.getGoles()
                .stream()
                .filter(gol -> gol.getPartido()
                        .equals(partido)).toList();

        jugador.setGoles(goles);
        return jugador;
    }

    private Jugador filterConvocadoByPartido(Jugador jugador, Partido partido) {
        var convocaciones = jugador.getConvocaciones()
                .stream()
                .filter(convocado -> convocado.getPartido()
                        .equals(partido)).toList();

        jugador.setConvocaciones(convocaciones);
        return jugador;
    }

    private Jugador filterCambioByPartido(Jugador jugador, Partido partido) {
        var historialSustituciones = jugador.getHistorialSustituciones()
                .stream()
                .filter(cambio -> cambio.getSustitucion().getPartido().equals(partido)).toList();

        jugador.setHistorialSustituciones(historialSustituciones);
        return jugador;
    }

    private Jugador filterTarjetasByPartido(Jugador jugador, Partido partido) {
        var tarjetas = jugador.getTarjetas()
                .stream()
                .filter(tarjeta -> tarjeta.getPartido()
                        .equals(partido)).toList();
        jugador.setTarjetas(tarjetas);
        return jugador;
    }
}
