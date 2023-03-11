package Doggie.WebPage.Mundial.dto.mapper;


import Doggie.WebPage.Mundial.dto.EquipoTablaFaseGrupos;
import Doggie.WebPage.Mundial.modelo.entidad.EquipoEnPartido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EquipoTablaFaseGruposMapper {

    @Mapping(target = "nombre", source = "equipo.nombre")
    @Mapping(target = "golesAFavor", source = "estadistica.goles")
    @Mapping(target = "golesEnContra", source = "estadistica.golesEnContra")
    @Mapping(target = "puntos", source = "estadistica.puntosResultados")
    EquipoTablaFaseGrupos toEquipoPartido(EquipoEnPartido relPartidosEquipos);
}


