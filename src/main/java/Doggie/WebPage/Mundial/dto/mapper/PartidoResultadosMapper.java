package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.PartidoResultados;
import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = EquipoResultadosMapper.class)
public interface PartidoResultadosMapper {

    @Mapping(target = "fase", source = "fase.nombre")
    @Mapping(target = "equipos", source = "relPartidosEquipos")
    PartidoResultados toPartidoResultados(Partido partido);
}
