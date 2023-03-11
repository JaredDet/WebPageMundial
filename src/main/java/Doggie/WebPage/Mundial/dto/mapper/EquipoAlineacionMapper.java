package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.EquipoAlineacion;
import Doggie.WebPage.Mundial.modelo.entidad.EquipoEnPartido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = JugadorAlineacionMapper.class)
public interface EquipoAlineacionMapper {

    @Mapping(target = "jugadores", source = "jugadores")
    @Mapping(target = "formacion", source = "formacion.secuencia")
    @Mapping(target = "director", source = "equipo.directorTecnico.nombre")
    EquipoAlineacion toEquipoAlineacion(EquipoEnPartido equipoEnPartido);

    List<EquipoAlineacion> toEquipoAlineacion(List<EquipoEnPartido> equipos);
}
