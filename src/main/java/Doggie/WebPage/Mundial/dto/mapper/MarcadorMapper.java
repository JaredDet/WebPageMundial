package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.GolesEquipo;
import Doggie.WebPage.Mundial.dto.GolesJugador;
import Doggie.WebPage.Mundial.dto.MarcadorEquipo;
import Doggie.WebPage.Mundial.modelo.entidad.Equipo;
import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")

public interface MarcadorMapper {


    default List<MarcadorEquipo> from(Partido partido) {

        var equipos = partido.getEquiposParticipantes();
        var local = equipos.get(0).getEquipo();
        var visita = equipos.get(1).getEquipo();

        var golesLocal = findGolesByPartidoAndEquipo(partido, local);
        var golesVisita = findGolesByPartidoAndEquipo(partido, visita);

        var marcadorLocal = new MarcadorEquipo(local.getPais().getNombre(), golesLocal);
        var marcadorVisita = new MarcadorEquipo(visita.getPais().getNombre(), golesVisita);

        return List.of(marcadorLocal, marcadorVisita);
    }

    default int findGolesByPartidoAndEquipo(Partido partido, Equipo equipo) {

        var listaGoles = partido.getGoles().stream()
                .filter(gol -> gol.getJugador().getEquipo().equals(equipo))
                .toList();

        return listaGoles.size();
    }
}
