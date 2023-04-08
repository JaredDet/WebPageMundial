package Doggie.WebPage.Mundial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DatosPartido(List<Plantilla> plantillas, List<MarcadorEquipo> marcador, List<GolesEquipo> equiposGoles, Date fecha,
                           Date hora, String estadio, DatosArbitro arbitro, String fase, List<DatosEstadistica> estadisticas) {
}
