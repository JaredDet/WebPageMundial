package Doggie.WebPage.Mundial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content=JsonInclude.Include.NON_NULL)

public record GolesJugador(String nombre, @JsonInclude(JsonInclude.Include.NON_EMPTY) List<DatosGol> goles) {
}
