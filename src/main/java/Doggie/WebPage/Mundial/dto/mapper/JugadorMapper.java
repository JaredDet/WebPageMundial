package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.GolPeticion;
import Doggie.WebPage.Mundial.dto.JugadorPeticion;
import Doggie.WebPage.Mundial.dto.SustitucionPeticion;
import Doggie.WebPage.Mundial.dto.TarjetaPeticion;
import Doggie.WebPage.Mundial.modelo.Color;
import Doggie.WebPage.Mundial.modelo.entidad.*;
import Doggie.WebPage.Mundial.modelo.repositorio.RepositorioPosicion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor

public class JugadorMapper {

    private final RepositorioPosicion repositorioPosicion;

    public List<Jugador> from(List<JugadorPeticion> jugadorPeticiones, Partido partido) {

        List<Jugador> jugadores = new ArrayList<>();

        for (JugadorPeticion jugadorPeticion : jugadorPeticiones) {
            jugadores.add(fromJugadorPeticionYPartido(jugadorPeticion, partido));
        }

        return jugadores;
    }

    private Jugador fromJugadorPeticionYPartido(JugadorPeticion jugadorPeticion, Partido partido) {
        var jugador = crearJugadorDesdePeticion(jugadorPeticion);
        agregarConvocacionAlPartido(jugadorPeticion, jugador, partido);
        agregarGolesAlPartido(jugadorPeticion, jugador, partido);
        agregarTarjetasAlPartido(jugadorPeticion, jugador, partido);
        agregarSustitucionAlPartido(jugadorPeticion, jugador, partido);
        return jugador;
    }

    private Jugador crearJugadorDesdePeticion(JugadorPeticion jugadorPeticion) {

        var jugador = new Jugador();
        jugador.setNombre(jugadorPeticion.getNombre());
        jugador.setConvocaciones(new ArrayList<>());
        jugador.setGoles(new ArrayList<>());
        jugador.setTarjetas(new ArrayList<>());
        jugador.setHistorialSustituciones(new ArrayList<>());
        return jugador;
    }

    private void agregarConvocacionAlPartido(JugadorPeticion jugadorPeticion, Jugador jugador, Partido partido) {
        var convocado = crearConvocadoDesdePeticion(jugadorPeticion);
        partido.getConvocados().add(convocado);
        jugador.getConvocaciones().add(convocado);
        convocado.setPartido(partido);
    }

    private void agregarGolesAlPartido(JugadorPeticion jugadorPeticion, Jugador jugador, Partido partido) {

        if (jugadorPeticion.getGoles() != null) {
            var goles = crearGolesDesdePeticion(jugadorPeticion.getGoles());
            goles.forEach(gol -> {
                jugador.getGoles().add(gol);
                partido.getGoles().add(gol);
                gol.setPartido(partido);
            });
        }
    }

    private void agregarTarjetasAlPartido(JugadorPeticion jugadorPeticion, Jugador jugador, Partido partido) {

        if (jugadorPeticion.getTarjetas() != null) {
            var tarjetas = crearTarjetasDesdePeticion(jugadorPeticion.getTarjetas());
            tarjetas.forEach(tarjeta -> {
                jugador.getTarjetas().add(tarjeta);
                partido.getTarjetas().add(tarjeta);
                tarjeta.setPartido(partido);
            });
        }
    }

    private void agregarSustitucionAlPartido(JugadorPeticion jugadorPeticion, Jugador jugador, Partido partido) {
        var sustitucionPeticion = jugadorPeticion.getSustitucion();
        if (sustitucionPeticion != null) {
            var sustitucion = crearSustitucionesDesdePeticion(sustitucionPeticion);
            jugador.getHistorialSustituciones().add(sustitucion);
            partido.getSustituciones().add(sustitucion.getSustitucion());
            sustitucion.getSustitucion().setPartido(partido);
        }
    }

    private Convocado crearConvocadoDesdePeticion(JugadorPeticion jugadorPeticion) {

        var posicion = repositorioPosicion.findByAbreviacion(jugadorPeticion.getPosicion());

        var convocado = new Convocado();

        convocado.setPosicion(posicion);
        convocado.setEsTitular(jugadorPeticion.isEsTitular());
        convocado.setEsCapitan(jugadorPeticion.isEsCapitan());
        convocado.setEsJugadorPartido(jugadorPeticion.isEsJugadorPartido());
        return convocado;
    }

    private List<Gol> crearGolesDesdePeticion(List<GolPeticion> goles) {
        return goles.stream().map(peticion -> {
            var gol = new Gol();
            gol.setEntro(peticion.isEntro());
            gol.setPenal(peticion.isPenal());
            gol.setMinuto(peticion.getMinuto());
            return gol;
        }).toList();
    }

    private List<Tarjeta> crearTarjetasDesdePeticion(List<TarjetaPeticion> tarjetas) {
        return tarjetas.stream().map(peticion -> {
            var tarjeta = new Tarjeta();
            tarjeta.setMinuto(peticion.minuto());
            tarjeta.setColor(Color.valueOf(peticion.color()));
            return tarjeta;
        }).toList();
    }

    private Cambio crearSustitucionesDesdePeticion(SustitucionPeticion peticion) {
        var cambio = new Cambio();

        cambio.setEntra(peticion.isEntro());
        var sustitucion = new Sustitucion();
        sustitucion.setMinuto(peticion.getMinuto());
        cambio.setSustitucion(sustitucion);

        return cambio;
    }
}
