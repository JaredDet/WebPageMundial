package Doggie.WebPage.Mundial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DatosGol(Integer minuto, @JsonInclude(JsonInclude.Include.NON_DEFAULT) Boolean esPenal) {
}
