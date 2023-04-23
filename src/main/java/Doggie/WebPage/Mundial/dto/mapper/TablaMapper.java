package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.TablaEquipo;
import Doggie.WebPage.Mundial.dto.Tabla;
import Doggie.WebPage.Mundial.modelo.entidad.Equipo;
import Doggie.WebPage.Mundial.modelo.entidad.Grupo;
import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TablaMapper {

    default Tabla from(List<Partido> partidos, Grupo grupo) {
        var equipos = grupo.getEquipos();
        var tablas = equipos.stream()
                .map(equipo -> {
                    actualizarPartidosJugados(equipo, partidos);
                    return from(equipo);
                }).sorted().toList();

        return new Tabla(grupo.getNombre(), tablas);
    }

    default TablaEquipo from(Equipo equipo) {

        int jugados = equipo.getPartidosParticipados().size();

        var ganados = equipo.ganados();
        var perdidos = equipo.perdidos();
        var empatados = equipo.empatados();

        var golesAFavor = equipo.goles();
        var golesEnContra = equipo.golesEnContra();

        int puntos = (ganados * 3) + (empatados);
        int diferenciaGoles = golesAFavor - golesEnContra;

        return new TablaEquipo(equipo.getNombre(), jugados, ganados,
                perdidos, empatados, golesAFavor, golesEnContra, diferenciaGoles, puntos);
    }

    default void actualizarPartidosJugados(Equipo equipo, List<Partido> partidos) {

        var participacionesGrupo = equipo.getPartidosParticipados().stream()
                .filter(participacion -> partidos.stream()
                        .anyMatch(partido -> participacion.getPartido().equals(partido)))
                .toList();
        equipo.setPartidosParticipados(participacionesGrupo);
    }
}
