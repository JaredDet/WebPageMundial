package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.DatosTecnico;
import Doggie.WebPage.Mundial.dto.JugadorPlantilla;
import Doggie.WebPage.Mundial.dto.Plantilla;
import Doggie.WebPage.Mundial.dto.JugadorEnfrentamiento;
import Doggie.WebPage.Mundial.modelo.entidad.*;
import org.mapstruct.Mapper;

import java.util.List;

import static java.util.Collections.sort;

@Mapper(componentModel = "spring")
public interface PlantillaMapper {

    JugadorEnfrentamientoMapper jugadorEnfrentamientoMapper = new JugadorEnfrentamientoMapperImpl();

    default List<Plantilla> from(List<Participante> equipos) {
        return equipos.stream().map(this::from).toList();
    }

    default Plantilla from(Participante equipo) {

        var jugadores = jugadorEnfrentamientoMapper.from(equipo.getJugadores(), equipo.getPartido());

        var titulares = jugadores.stream().filter(JugadorEnfrentamiento::esTitular).toList();
        var plantillaTitular = fromJugadores(titulares);

        var banca = jugadores.stream().filter(jugador -> !jugador.esTitular()).toList();
        var plantillaBanca = fromJugadores(banca);

        var entrenador = equipo.tecnico();
        var tecnico = new DatosTecnico(entrenador.getNombre(), entrenador.pais());

        return new Plantilla(equipo.getNombre(), plantillaTitular, plantillaBanca, equipo.isEsLocal(), !equipo.isEsLocal(), tecnico);
    }

    default List<JugadorPlantilla> fromJugadores(List<JugadorEnfrentamiento> jugadores) {
        return jugadores.stream().map(this::from).toList();
    }

    default JugadorPlantilla from(JugadorEnfrentamiento jugador) {
        return new JugadorPlantilla(jugador.nombre(), jugador.dorsal(), jugador.posicion(), jugador.esCapitan(), jugador.esJugadorPartido(), jugador.tarjetas(), jugador.sustitucion());
    }
}
