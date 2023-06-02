package Doggie.WebPage.Mundial.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AlineacionTitular(List<JugadorPlantilla> portero, List<JugadorPlantilla> defensas,
                                List<JugadorPlantilla> laterales, List<JugadorPlantilla> medioCentroDefensivo,
                                List<JugadorPlantilla> medioCentro,
                                List<JugadorPlantilla> medioCentroOfensivo,
                                List<JugadorPlantilla> delanteros) {
}
