package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.EquipoResultados;
import Doggie.WebPage.Mundial.modelo.entidad.EquipoEnPartido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EquipoResultadosMapper {

    @Mapping(target = "nombre", source = "equipo.nombre")
    @Mapping(target = "goles", source = "estadistica.goles")
    EquipoResultados toEquipoPartidoResultados(EquipoEnPartido relPartidosEquipos);
}
