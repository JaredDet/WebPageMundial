package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.DatosEstadistica;
import Doggie.WebPage.Mundial.modelo.Color;
import Doggie.WebPage.Mundial.modelo.entidad.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DatosEstadisticaMapper {

    @Mapping(target = "nombreEquipo", source = "equipo.pais.nombre")
    @Mapping(target = "remates", source = "estadisticas.remates")
    @Mapping(target = "rematesAlArco", source = "estadisticas.rematesAlArco")
    @Mapping(target = "posesion", source = "estadisticas.posesion")
    @Mapping(target = "pases", source = "estadisticas.pases")
    @Mapping(target = "precisionPases", source = "estadisticas.precisionPases")
    @Mapping(target = "faltas", source = "estadisticas.faltas")
    @Mapping(target = "posicionesAdelantadas", source = "estadisticas.posicionesAdelantadas")
    @Mapping(target = "tirosEsquina", source = "estadisticas.tirosEsquina")
    @Mapping(target = "tarjetasAmarillas", source = "equipo.jugadores", qualifiedByName = "tarjetasAmarillas")
    @Mapping(target = "tarjetasRojas", source = "equipo.jugadores", qualifiedByName = "tarjetasRojas")
    DatosEstadistica from(Participante participante);

    List<DatosEstadistica> from(List<Participante> participantes);

    @Named("tarjetasAmarillas")

    //Flatmap
    default int tarjetasAmarillas(List<Jugador> jugadores) {
        var tarjetas = tarjetasFrom(jugadores);
        return numeroTarjetas(tarjetas, Color.AMARILLA);
    }

    @Named("tarjetasRojas")
    default int tarjetasRojas(List<Jugador> jugadores) {
        var tarjetas = tarjetasFrom(jugadores);
        return numeroTarjetas(tarjetas, Color.ROJA);
    }

    default List<Tarjeta> tarjetasFrom(List<Jugador> jugadores) {
        var jugadoresConTarjetas = jugadores.stream()
                .filter(jugador -> jugador.getTarjetas() != null).toList();

        return jugadoresConTarjetas.stream()
                .flatMap(jugador -> jugador.getTarjetas().stream()).toList();
    }

    default int numeroTarjetas(List<Tarjeta> tarjetas, Color color) {
        return (int) tarjetas.stream()
                .filter(tarjeta -> tarjeta.getColor()
                        .equals(color))
                .count();
    }
}
