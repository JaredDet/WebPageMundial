package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.JugadorAlineacion;
import Doggie.WebPage.Mundial.modelo.Color;
import Doggie.WebPage.Mundial.modelo.entidad.JugadorEnPartido;
import Doggie.WebPage.Mundial.modelo.entidad.Tarjeta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JugadorAlineacionMapper {

    @Mapping(target = "tieneTarjetaAmarilla", source = "tarjetas", qualifiedByName = "tarjetasToTarjetaAmarilla")
    @Mapping(target = "tieneTarjetaRoja", source = "tarjetas", qualifiedByName = "tarjetasToTarjetaRoja")
    @Mapping(target = "nombre", source = "jugador.nombre")
    @Mapping(target = "dorsal", source = "jugador.dorsal")
    JugadorAlineacion toJugador(JugadorEnPartido jugadorEnPartido);

    List<JugadorAlineacion> toJugador(List<JugadorEnPartido> jugadores);

    @Named("tarjetasToTarjetaAmarilla")
    default boolean tarjetasToTarjetaAmarilla(List<Tarjeta> tarjetas) {
        return tarjetas.stream()
                .anyMatch(tarjeta -> tarjeta.getColorTarjeta() == Color.AMARILLA);
    }

    @Named("tarjetasToTarjetaRoja")
    default boolean tarjetasToTarjetaRoja(List<Tarjeta> tarjetas) {
        return tarjetas.stream()
                .anyMatch(tarjeta -> tarjeta.getColorTarjeta() == Color.ROJA);
    }
}
