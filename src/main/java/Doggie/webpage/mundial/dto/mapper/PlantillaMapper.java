package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.*;
import Doggie.WebPage.Mundial.modelo.entidad.*;
import org.mapstruct.Mapper;

import javax.sound.sampled.Line;
import java.util.List;
import java.util.stream.Collectors;

import static Doggie.WebPage.Mundial.dto.PosicionJugadores.*;
import static java.util.Collections.sort;

@Mapper(componentModel = "spring")
public interface PlantillaMapper {

    JugadorEnfrentamientoMapper jugadorEnfrentamientoMapper = new JugadorEnfrentamientoMapperImpl();

    default List<Plantilla> from(List<Participante> equipos) {
        return equipos.stream().map(this::from).toList();
    }

    default Plantilla from(Participante equipo) {

        var jugadores = jugadorEnfrentamientoMapper.from(equipo.jugadores(), equipo.getPartido());

        var titulares = jugadores.stream().filter(JugadorEnfrentamiento::esTitular).toList();
        var banca = jugadores.stream().filter(jugador -> !jugador.esTitular()).toList();
        var bancaPlantilla = fromJugadores(banca);

        var entrenador = equipo.tecnico();
        var tecnico = new DatosTecnico(entrenador.getNombre(), entrenador.pais());

        var alineacion = fromTitulares(titulares);

        return new Plantilla(equipo.getNombre(), equipo.getCodigo(), alineacion, bancaPlantilla, equipo.isEsLocal(), !equipo.isEsLocal(), tecnico, equipo.formacion(), equipo.posicionLaterales());
    }

    default AlineacionTitular fromTitulares(List<JugadorEnfrentamiento> titulares) {

        var lineas = titulares.stream()
                .collect(Collectors.groupingBy(
                        JugadorEnfrentamiento::abreviatura,
                        Collectors.mapping(this::fromJugador, Collectors.toList())));

        return new AlineacionTitular(lineas.get("POR"),
                lineas.get("DEF"),
                lineas.get("LAT"),
                lineas.get("MCD"),
                lineas.get("MC"),
                lineas.get("MCO"),
                lineas.get("DEL"));
    }

    default List<JugadorPlantilla> fromJugadores(List<JugadorEnfrentamiento> jugadores) {
        return jugadores.stream()
                .map(this::fromJugador)
                .toList();
    }

    default JugadorPlantilla fromJugador(JugadorEnfrentamiento jugador) {

        var salio = jugador.entro() != null ? !jugador.entro() : null;
        return new JugadorPlantilla(jugador.nombre(), jugador.dorsal(), jugador.lado(), jugador.esCapitan(),
                jugador.esJugadorPartido(), jugador.tarjetaAmarilla(), jugador.tarjetaRoja(), jugador.gol(), jugador.entro(), salio);
    }
}
