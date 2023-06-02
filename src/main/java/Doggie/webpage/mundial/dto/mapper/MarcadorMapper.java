package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.MarcadorEquipo;
import Doggie.WebPage.Mundial.modelo.entidad.Participante;
import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.function.IntSupplier;


@Mapper(componentModel = "spring")

public interface MarcadorMapper {

    default List<MarcadorEquipo> marcadorGolesFrom(Partido partido) {
        return marcadorFrom(partido, true);
    }

    default List<MarcadorEquipo> marcadorPenalesFrom(Partido partido) {
        return marcadorFrom(partido, false);
    }

    default List<MarcadorEquipo> marcadorFrom(Partido partido, boolean goles) {

        var equipos = partido.getEquiposParticipantes();
        var local = equipos.get(0);
        var visita = equipos.get(1);

        IntSupplier calculaMarcadorLocal = goles ? local::goles : local::penales;
        IntSupplier calculaMarcadorVisita = goles ? visita::goles : visita::penales;

        return getMarcador(local, visita, calculaMarcadorLocal, calculaMarcadorVisita);
    }

    default List<MarcadorEquipo> getMarcador(Participante local, Participante visita, IntSupplier calculaMarcadorLocal, IntSupplier calculaMarcadorVisita) {

        var marcadorLocal = new MarcadorEquipo(local.getNombre(), local.getCodigo(), calculaMarcadorLocal.getAsInt());
        var marcadorVisita = new MarcadorEquipo(visita.getNombre(), visita.getCodigo(), calculaMarcadorVisita.getAsInt());

        return List.of(marcadorLocal, marcadorVisita);
    }
}


