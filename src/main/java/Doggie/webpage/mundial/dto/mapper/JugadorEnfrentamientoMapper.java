package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.JugadorEnfrentamiento;
import Doggie.WebPage.Mundial.modelo.entidad.Jugador;
import Doggie.WebPage.Mundial.modelo.entidad.Partido;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JugadorEnfrentamientoMapper {

    default List<JugadorEnfrentamiento> from(List<Jugador> jugadores, Partido partido) {
        return jugadores.stream().map(jugador -> from(jugador, partido)).
                sorted().toList();
    }

    default JugadorEnfrentamiento from(Jugador jugador, Partido partido) {

        var participacion = jugador.getConvocacionPorPartido(partido);
        var sustitucion = jugador.getSustitucionPorPartido(partido);
        var posicion = participacion.getPosicion();
        var titular = participacion.isEsTitular();
        var mvp = participacion.isEsJugadorPartido();
        var capitan = participacion.isEsCapitan();

        var tarjetaAmarilla = jugador.tuvoTarjetaAmarillaEnPartido(partido);
        var tarjetaRoja = jugador.tuvoTarjetaRojaEnPartido(partido);

        var gol = jugador.anotoGolesEnPartido(partido);

        var posicionNombre = posicion != null ? posicion.getNombre() : null;
        var abreviatura = posicion != null ? posicion.abreviatura() : null;
        var lado = posicion != null ? posicion.lado() : null;

        var entra = sustitucion != null ? sustitucion.isEntra() : null;

        var nombre = titular ? jugador.getNombreTitular() : jugador.getNombre();
        return new JugadorEnfrentamiento(nombre, jugador.getDorsal(),
                posicionNombre, abreviatura, lado, capitan, mvp, titular, tarjetaAmarilla, tarjetaRoja, gol, entra);
    }
}
