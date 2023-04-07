package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.DatosTecnico;
import Doggie.WebPage.Mundial.dto.JugadorPlantilla;
import Doggie.WebPage.Mundial.dto.Plantilla;
import Doggie.WebPage.Mundial.dto.JugadorEnfrentamiento;
import Doggie.WebPage.Mundial.modelo.entidad.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlantillaMapper {

    JugadorEnfrentamientoMapper jugadorEnfrentamientoMapper = new JugadorEnfrentamientoMapperImpl();

    default Plantilla from(Equipo equipo) {

        var participante = equipo.getPartidosParticipados().get(0);

        var jugadores = jugadorEnfrentamientoMapper.from(equipo.getJugadores());

        var titulares = jugadores.stream().filter(JugadorEnfrentamiento::esTitular).toList();
        var plantillaTitular = from(titulares);

        var banca = jugadores.stream().filter(jugador -> !jugador.esTitular()).toList();
        var plantillaBanca = from(banca);

        var entrenador = equipo.getTecnico();
        var tecnico = new DatosTecnico(entrenador.getNombre(), entrenador.getPais().getNombre());

        return new Plantilla(equipo.getPais().getNombre(), plantillaTitular, plantillaBanca, participante.isEsLocal(), !participante.isEsLocal(), tecnico);
    }

    default List<JugadorPlantilla> from(List<JugadorEnfrentamiento> jugadores) {
        return jugadores.stream().map(this::from).toList();
    }

    default JugadorPlantilla from(JugadorEnfrentamiento jugador) {
        return new JugadorPlantilla(jugador.nombre(), jugador.dorsal(), jugador.posicion(), jugador.esCapitan(), jugador.esJugadorPartido(), jugador.tarjetas(), jugador.sustitucion());
    }
}
