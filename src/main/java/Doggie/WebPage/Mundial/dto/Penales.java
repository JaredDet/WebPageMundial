package Doggie.WebPage.Mundial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public record Penales(List<PenalJugador> penales) {
}
