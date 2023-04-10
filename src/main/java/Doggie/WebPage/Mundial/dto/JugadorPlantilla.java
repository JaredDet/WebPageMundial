package Doggie.WebPage.Mundial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record JugadorPlantilla(String nombre, int dorsal, String posicion,
                               @JsonInclude(JsonInclude.Include.NON_DEFAULT) Boolean esCapitan,
                               @JsonInclude(JsonInclude.Include.NON_DEFAULT) Boolean esJugadorPartido,
                               @JsonInclude(JsonInclude.Include.NON_EMPTY) List<DatosTarjeta> tarjetas, DatosSustitucion sustitucion) {
}
