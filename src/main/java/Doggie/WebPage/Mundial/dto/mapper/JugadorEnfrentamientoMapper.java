package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.DatosSustitucion;
import Doggie.WebPage.Mundial.dto.DatosTarjeta;
import Doggie.WebPage.Mundial.dto.JugadorEnfrentamiento;
import Doggie.WebPage.Mundial.modelo.entidad.Cambio;
import Doggie.WebPage.Mundial.modelo.entidad.Convocado;
import Doggie.WebPage.Mundial.modelo.entidad.Jugador;
import Doggie.WebPage.Mundial.modelo.entidad.Tarjeta;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JugadorEnfrentamientoMapper {

    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "dorsal", source = "dorsal")
    @Mapping(target = "posicion", source = "convocaciones", qualifiedByName = "posicion")
    @Mapping(target = "tarjetas", source = "tarjetas", qualifiedByName = "tarjetasMapping")
    @Mapping(target = "sustitucion", source = "historialSustituciones", qualifiedByName = "cambio")
    @Mapping(target = "esTitular", source = "convocaciones", qualifiedByName = "titular")
    @Mapping(target = "esJugadorPartido", source = "convocaciones", qualifiedByName = "mvp")
    @Mapping(target = "esCapitan", source = "convocaciones", qualifiedByName = "capitan")
    JugadorEnfrentamiento from(Jugador jugador);

    List<JugadorEnfrentamiento> from(List<Jugador> jugadores);

    @Named("posicion")
    default String posicion(List<Convocado> convocaciones) {
        return convocaciones.get(0).getPosicion().getAbreviacion();
    }

    @Named("titular")
    default boolean titular(List<Convocado> convocaciones) {
        return convocaciones.get(0).isEsTitular();
    }

    @Named("mvp")
    default boolean mvp(List<Convocado> convocaciones) {
        return convocaciones.get(0).isEsJugadorPartido();
    }

    @Named("capitan")
    default boolean capitan(List<Convocado> convocaciones) {
        return convocaciones.get(0).isEsCapitan();
    }

    @Named("tarjetasMapping")
    default List<DatosTarjeta> tarjetasMapping(List<Tarjeta> tarjetas) {
        return tarjetas.stream()
                .map(tarjeta -> new DatosTarjeta(tarjeta.getColor().name(), tarjeta.getMinuto()))
                .toList();
    }

    @Named("cambio")
    default DatosSustitucion cambio(List<Cambio> historialSustituciones) {

        if (historialSustituciones.isEmpty()) {
            return null;
        }

        var cambio = historialSustituciones.get(0);
        return new DatosSustitucion(cambio.isEntra(), !cambio.isEntra(), cambio.getSustitucion().getMinuto());
    }
}
