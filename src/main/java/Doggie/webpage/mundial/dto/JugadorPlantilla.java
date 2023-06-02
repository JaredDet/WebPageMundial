package Doggie.WebPage.Mundial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record JugadorPlantilla(String nombre, int dorsal, @JsonInclude(JsonInclude.Include.NON_NULL) String lado,
                               @JsonInclude(JsonInclude.Include.NON_DEFAULT) Boolean capitan,
                               @JsonInclude(JsonInclude.Include.NON_DEFAULT) Boolean jugadorPartido,
                               @JsonInclude(JsonInclude.Include.NON_DEFAULT) Boolean tarjetaAmarilla,
                               @JsonInclude(JsonInclude.Include.NON_DEFAULT) Boolean tarjetaRoja,
                               @JsonInclude(JsonInclude.Include.NON_DEFAULT) Boolean gol,
                               @JsonInclude(JsonInclude.Include.NON_DEFAULT) Boolean entro,
                               @JsonInclude(JsonInclude.Include.NON_DEFAULT) Boolean salio) {
}
