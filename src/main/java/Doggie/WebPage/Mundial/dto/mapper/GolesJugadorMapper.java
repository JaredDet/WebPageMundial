package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.GolesJugador;
import Doggie.WebPage.Mundial.modelo.entidad.Gol;
import Doggie.WebPage.Mundial.modelo.entidad.Jugador;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")

public interface GolesJugadorMapper {

    @Mapping(target = "minutos", source = "goles", qualifiedByName = "minutosGoles")
    GolesJugador from(Jugador jugador);

    @Named("minutosGoles")
    default List<Integer> minutosGoles(List<Gol> goles) {
        return goles.stream().map(Gol::getMinuto).toList();
    }
}
