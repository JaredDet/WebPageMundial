package Doggie.WebPage.Mundial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GolesEquipo(String nombre, @JsonInclude(JsonInclude.Include.NON_EMPTY) List<GolesJugador> goles) {
}
