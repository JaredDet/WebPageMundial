package Doggie.WebPage.Mundial.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DatosPartido(@JsonInclude(JsonInclude.Include.NON_EMPTY) List<Plantilla> plantillas,
                           @JsonInclude(JsonInclude.Include.NON_EMPTY) List<MarcadorEquipo> marcador,
                           @JsonInclude(JsonInclude.Include.NON_EMPTY) List<GolesEquipo> equiposGoles,
                           @JsonFormat(pattern = "d 'de' MMMM 'de' yyyy") Date fecha,
                           Date hora, String estadio, DatosArbitro arbitro, String fase,
                           @JsonInclude(JsonInclude.Include.NON_EMPTY) List<DatosEstadistica> estadisticas,
                           @JsonInclude(JsonInclude.Include.NON_EMPTY) List<MarcadorEquipo> marcadorPenales,
                           @JsonInclude(JsonInclude.Include.NON_EMPTY) List<Penales> penales) {
}
