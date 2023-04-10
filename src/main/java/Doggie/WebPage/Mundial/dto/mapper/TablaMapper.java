package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.TablaEquipo;
import Doggie.WebPage.Mundial.dto.Tabla;
import Doggie.WebPage.Mundial.modelo.entidad.Equipo;
import Doggie.WebPage.Mundial.modelo.entidad.Grupo;
import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

@Mapper(componentModel = "spring")
public interface TablaMapper {

    default Tabla from(List<Partido> partidos, Grupo grupo) {
        var equipos = grupo.getEquipos();
        var tablas = equipos.stream()
                .map(equipo -> {
                    var partidosEquipo = filter(partidos, equipo);
                    return from(equipo, partidosEquipo);
                }).toList();

        return new Tabla(grupo.getNombre(), tablas);
    }

    default TablaEquipo from(Equipo equipo, List<Partido> partidos) {

        int jugados = partidos.size();

        var ganados = calcular(partidos, equipo::gana);
        var perdidos = calcular(partidos, equipo::pierde);
        var empatados = calcular(partidos, equipo::empata);

        var golesAFavor = calcularNumeroGoles(partidos, partido -> partido.golesEquipo(equipo));
        var golesEnContra = calcularNumeroGoles(partidos, partido -> partido.golesRival(equipo));

        int puntos = (ganados * 3) + (empatados);
        int diferenciaGoles = golesAFavor - golesEnContra;

        return new TablaEquipo(equipo.getPais().getNombre(), jugados, ganados,
                perdidos, empatados, golesAFavor, golesEnContra, diferenciaGoles, puntos);
    }

    default int calcular(List<Partido> partidos, Predicate<Partido> predicado) {
        return (int) partidos.stream()
                .filter(predicado).count();
    }

    default int calcularNumeroGoles(List<Partido> partidos, ToIntFunction<Partido> funcion) {
        return partidos.stream().mapToInt(funcion).sum();
    }

    default List<Partido> filter(List<Partido> partidos, Equipo equipo) {
       return partidos.stream()
                .filter(partido -> partido.getEquiposParticipantes()
                        .stream().anyMatch(participante -> participante.getEquipo()
                                .equals(equipo))).toList();
    }
}
