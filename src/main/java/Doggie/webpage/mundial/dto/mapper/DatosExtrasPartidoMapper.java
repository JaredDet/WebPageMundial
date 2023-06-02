package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.DatosArbitro;
import Doggie.WebPage.Mundial.dto.DatosExtrasPartido;
import Doggie.WebPage.Mundial.modelo.entidad.Arbitro;
import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DatosExtrasPartidoMapper {

    default DatosExtrasPartido from(Partido partido) {

        var datosArbitro = getDatosArbitro(partido.getArbitro());
        var fase = partido.getFase().getNombre();

        return new DatosExtrasPartido(partido.getFecha(),
                partido.getHora(),
                partido.getEstadio().getNombre(),
                datosArbitro,
                fase);
    }

    private DatosArbitro getDatosArbitro(Arbitro arbitro) {
        return new DatosArbitro(arbitro.getNombre(), arbitro.pais());
    }
}
