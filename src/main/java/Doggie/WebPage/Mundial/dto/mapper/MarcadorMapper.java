package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.GolesEquipo;
import Doggie.WebPage.Mundial.dto.Marcador;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")

public interface MarcadorMapper {

    default Marcador from(GolesEquipo local, GolesEquipo visita) {
        var golesLocal = local.goles().size();
        var golesVisita = visita.goles().size();
        return new Marcador(golesLocal, golesVisita);
    }
}
