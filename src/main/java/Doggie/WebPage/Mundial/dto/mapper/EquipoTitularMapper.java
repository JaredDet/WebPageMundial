package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.EquipoTitular;
import Doggie.WebPage.Mundial.modelo.entidad.Equipo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = JugadorTitularMapper.class)
public interface EquipoTitularMapper {

    EquipoTitular toEquipoTitular(Equipo equipo);
}
