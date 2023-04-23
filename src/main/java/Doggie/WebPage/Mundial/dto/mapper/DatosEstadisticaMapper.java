package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.DatosEstadistica;
import Doggie.WebPage.Mundial.modelo.entidad.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DatosEstadisticaMapper {

    List<DatosEstadistica> from(List<Participante> equipos);
    @Mapping(target = "nombreEquipo", source = "equipo.pais.nombre")
    @Mapping(target = "remates", source = "estadisticas.remates")
    @Mapping(target = "rematesAlArco", source = "estadisticas.rematesAlArco")
    @Mapping(target = "posesion", source = "estadisticas.posesion")
    @Mapping(target = "pases", source = "estadisticas.pases")
    @Mapping(target = "precisionPases", source = "estadisticas.precisionPases")
    @Mapping(target = "faltas", source = "estadisticas.faltas")
    @Mapping(target = "posicionesAdelantadas", source = "estadisticas.posicionesAdelantadas")
    @Mapping(target = "tirosEsquina", source = "estadisticas.tirosEsquina")
    @Mapping(target = "tarjetasAmarillas", source = "tarjetasAmarillas")
    @Mapping(target = "tarjetasRojas", source = "tarjetasRojas")
    DatosEstadistica from(Participante participante);
}
