package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.EquipoGoles;
import Doggie.WebPage.Mundial.dto.Marcador;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")

public interface MarcadorMapper {

    default Marcador from(EquipoGoles local, EquipoGoles visita) {
        var golesLocal = local.goles().size();
        var golesVisita = visita.goles().size();
        return new Marcador(golesLocal, golesVisita);
    }
}
