package Doggie.WebPage.Mundial.servicio;

import Doggie.WebPage.Mundial.modelo.entidad.*;
import Doggie.WebPage.Mundial.modelo.repositorio.RepositorioGol;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ServicioJugador {
    private final JugadorCache jugadorCache;
    private final RepositorioGol repositorioGol;

    public List<Jugador> findByEquipoYPartido(Equipo equipo, Partido partido) {
        var jugadores = jugadorCache.getJugadoresConvocados(equipo.getEquipoId(), partido.getPartidoId());
        return jugadores.stream()
                .map(jugador -> filterConvocadoByPartido(jugador, partido))
                .map(jugador -> filterCambioByPartido(jugador, partido))
                .map(jugador -> filterTarjetasByPartido(jugador, partido)).toList();
    }

    public List<Jugador> findJugadorConGolesByEquipoYPartido(Equipo equipo, Partido partido, boolean soloPenales) {
        return jugadorCache.getJugadoresConGoles(equipo.getEquipoId(), partido.getPartidoId())
                .stream()
                .peek(jugador -> jugador.setGoles(
                        soloPenales ? repositorioGol.findPenales(jugador.getJugadorId(), partido.getPartidoId())
                                : repositorioGol.findGoles(jugador.getJugadorId(), partido.getPartidoId())))
                .filter(jugador -> !jugador.getGoles().isEmpty())
                .toList();
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
