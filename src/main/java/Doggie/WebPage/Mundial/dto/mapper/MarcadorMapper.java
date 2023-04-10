package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.MarcadorEquipo;
import Doggie.WebPage.Mundial.modelo.entidad.Equipo;
import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.function.ToIntFunction;


@Mapper(componentModel = "spring")

public interface MarcadorMapper {


    default List<MarcadorEquipo> marcadorGolesFrom(Partido partido) {
        return getMarcadores(partido, partido::golesEquipo);
    }

    default List<MarcadorEquipo> marcadorPenalesFrom(Partido partido) {
        return getMarcadores(partido, partido::penales);
    }

    private List<MarcadorEquipo> getMarcadores(Partido partido, ToIntFunction<Equipo> golesFunction) {
        var equipos = partido.getEquiposParticipantes();
        var local = equipos.get(0).getEquipo();
        var visita = equipos.get(1).getEquipo();

        var golesLocal = golesFunction.applyAsInt(local);
        var golesVisita = golesFunction.applyAsInt(visita);

        var marcadorLocal = new MarcadorEquipo(local.getPais().getNombre(), golesLocal);
        var marcadorVisita = new MarcadorEquipo(visita.getPais().getNombre(), golesVisita);

        return List.of(marcadorLocal, marcadorVisita);
    }
}
