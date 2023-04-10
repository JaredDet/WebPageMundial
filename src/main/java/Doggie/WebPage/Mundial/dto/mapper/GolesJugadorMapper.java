package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.DatosGol;
import Doggie.WebPage.Mundial.dto.GolesJugador;
import Doggie.WebPage.Mundial.modelo.entidad.Gol;
import Doggie.WebPage.Mundial.modelo.entidad.Jugador;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")

public interface GolesJugadorMapper {

    @Mapping(target = "goles", source = "goles", qualifiedByName = "goles")
    GolesJugador from(Jugador jugador);

    @Named("goles")
    default List<DatosGol> goles(List<Gol> golesJugador) {
        return golesJugador.stream()
                .filter(gol -> gol.getMinuto() != null)
                .map(gol -> new DatosGol(gol.getMinuto(),
                        gol.isPenal()))
                .toList();
    }
}
