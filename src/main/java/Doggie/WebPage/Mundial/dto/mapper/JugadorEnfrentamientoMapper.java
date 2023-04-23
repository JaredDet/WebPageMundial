package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.DatosSustitucion;
import Doggie.WebPage.Mundial.dto.DatosTarjeta;
import Doggie.WebPage.Mundial.dto.JugadorEnfrentamiento;
import Doggie.WebPage.Mundial.modelo.entidad.Cambio;
import Doggie.WebPage.Mundial.modelo.entidad.Jugador;
import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import Doggie.WebPage.Mundial.modelo.entidad.Tarjeta;

import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface JugadorEnfrentamientoMapper {

    default List<JugadorEnfrentamiento> from(List<Jugador> jugadores, Partido partido) {
        return jugadores.stream().map(jugador -> from(jugador, partido)).
                sorted().toList();
    }

    default JugadorEnfrentamiento from(Jugador jugador, Partido partido) {

        var participacion = jugador.getConvocacionPorPartido(partido);

        var posicion = participacion.getPosicion();
        var titular = participacion.isEsTitular();
        var mvp = participacion.isEsJugadorPartido();
        var capitan = participacion.isEsCapitan();

        var tarjetas = tarjetasMapping(jugador.getTarjetasPorPartido(partido));

        var cambio = sustitucionMapping(jugador.getSustitucionPorPartido(partido));

        var posicionNombre = posicion != null ? posicion.getAbreviacion() : null;

        return new JugadorEnfrentamiento(jugador.getNombre(), jugador.getDorsal(), posicionNombre, capitan, mvp, titular, tarjetas, cambio);
    }

    default List<DatosTarjeta> tarjetasMapping(List<Tarjeta> tarjetas) {
        return tarjetas.stream()
                .map(tarjeta -> new DatosTarjeta(tarjeta.getColor().name(), tarjeta.getMinuto()))
                .toList();
    }

    default DatosSustitucion sustitucionMapping(Cambio sustitucion) {

        return Optional.ofNullable(sustitucion)
                .map(cambio -> new DatosSustitucion(cambio.isEntra(), !cambio.isEntra(), cambio.getSustitucion().getMinuto()))
                .orElse(null);
    }
}
