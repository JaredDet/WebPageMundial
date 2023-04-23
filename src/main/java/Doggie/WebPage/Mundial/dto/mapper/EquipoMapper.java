package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.EquipoPeticion;
import Doggie.WebPage.Mundial.excepciones.JugadorNoEncontradoException;
import Doggie.WebPage.Mundial.modelo.entidad.Cambio;
import Doggie.WebPage.Mundial.modelo.entidad.Equipo;
import Doggie.WebPage.Mundial.modelo.entidad.Jugador;
import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import Doggie.WebPage.Mundial.modelo.repositorio.RepositorioEquipo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor

public class EquipoMapper {

    private final JugadorMapper jugadorMapper;

    private final RepositorioEquipo repositorioEquipo;


    public Equipo from(EquipoPeticion peticion, Partido partido) {

        var equipo = repositorioEquipo.findByNombre(peticion.nombre());
        var jugadoresPeticion = jugadorMapper.from(peticion.jugadores(), partido);
        var jugadores = equipo.getJugadores();

        if (!jugadores.isEmpty()) {
            actualizarJugadores(jugadores, jugadoresPeticion);
        }
        return equipo;
    }

    private void actualizarJugadores(List<Jugador> jugadores, List<Jugador> peticiones) {

        peticiones.forEach(peticion -> {
            var jugador = jugadores.stream()
                    .filter(jugadorBuscado -> peticion.getNombre().equals(jugadorBuscado.getNombre()))
                    .findFirst()
                    .orElseThrow(() -> new JugadorNoEncontradoException(peticion.getNombre()));

            jugador.getConvocaciones().addAll(peticion.getConvocaciones());
            jugador.getTarjetas().addAll(peticion.getTarjetas());
            jugador.getGoles().addAll(peticion.getGoles());
            jugador.getHistorialSustituciones().addAll(peticion.getHistorialSustituciones());
            actualizarDatos(jugador, peticion);

        });
    }

    private void actualizarDatos(Jugador jugador, Jugador peticion) {
        peticion.getConvocaciones().forEach(convocacion -> convocacion.setJugador(jugador));
        peticion.getTarjetas().forEach(tarjeta -> tarjeta.setJugador(jugador));
        peticion.getGoles().forEach(gol -> gol.setJugador(jugador));
        peticion.getHistorialSustituciones().forEach(sustitucion -> sustitucion.setJugador(jugador));
    }
}

