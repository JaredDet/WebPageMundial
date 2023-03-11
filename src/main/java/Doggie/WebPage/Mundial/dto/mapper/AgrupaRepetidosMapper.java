package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.EquipoTablaFaseGrupos;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface AgrupaRepetidosMapper {

    default List<EquipoTablaFaseGrupos> mapToEquiposSinRepetir(Map<String, List<EquipoTablaFaseGrupos>> equiposAgrupadosRepetidos) {
        var equipos = new ArrayList<EquipoTablaFaseGrupos>();

        for (var equipo : equiposAgrupadosRepetidos.entrySet()) {
            var goles = 0;
            var golesEnContra = 0;
            var puntos = 0;
            var ganados = 0;
            var empates = 0;
            var perdidos = 0;

            for (var replica : equipo.getValue()) {
                goles += replica.golesAFavor();
                golesEnContra += replica.golesEnContra();
                puntos += replica.puntos();

                if (goles > golesEnContra) {
                    ganados++;
                } else if (goles < golesEnContra) {
                    perdidos++;
                } else {
                    empates++;
                }
            }

            equipos.add(new EquipoTablaFaseGrupos(equipo.getKey(), equipo.getValue().size(), ganados, empates, perdidos, goles, golesEnContra, goles - golesEnContra, puntos));
        }
        Collections.sort(equipos);
        return equipos;
    }
}
