package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.DatosSustitucion;
import Doggie.WebPage.Mundial.dto.DatosTarjeta;
import Doggie.WebPage.Mundial.dto.JugadorEnfrentamiento;
import Doggie.WebPage.Mundial.modelo.entidad.Cambio;
import Doggie.WebPage.Mundial.modelo.entidad.Jugador;
import Doggie.WebPage.Mundial.modelo.entidad.Tarjeta;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JugadorEnfrentamientoMapper {

    default JugadorEnfrentamiento from(Jugador jugador) {

        var participacion = jugador.getConvocaciones().get(0);
        var posicion = participacion.getPosicion();
        var titular = participacion.isEsTitular();
        var mvp = participacion.isEsJugadorPartido();
        var capitan = participacion.isEsCapitan();

        var tarjetas = tarjetasMapping(jugador.getTarjetas());
        var cambio = cambio(jugador.getHistorialSustituciones());

        var posicionNombre = posicion != null ? posicion.getAbreviacion() : null;

        return new JugadorEnfrentamiento(jugador.getNombre(), jugador.getDorsal(), posicionNombre, capitan, mvp, titular, tarjetas, cambio);
    }

    List<JugadorEnfrentamiento> from(List<Jugador> jugadores);

    default List<DatosTarjeta> tarjetasMapping(List<Tarjeta> tarjetas) {
        return tarjetas.stream()
                .map(tarjeta -> new DatosTarjeta(tarjeta.getColor().name(), tarjeta.getMinuto()))
                .toList();
    }

    default DatosSustitucion cambio(List<Cambio> historialSustituciones) {

        if (historialSustituciones.isEmpty()) {
            return null;
        }

        var cambio = historialSustituciones.get(0);
        return new DatosSustitucion(cambio.isEntra(), !cambio.isEntra(), cambio.getSustitucion().getMinuto());
    }
}
