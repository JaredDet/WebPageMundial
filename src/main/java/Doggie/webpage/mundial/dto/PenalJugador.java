package Doggie.WebPage.Mundial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PenalJugador(String nombre, @JsonInclude(JsonInclude.Include.NON_DEFAULT) Boolean entro, @JsonInclude(JsonInclude.Include.NON_DEFAULT) Boolean fallo) {
}
