package Doggie.WebPage.Mundial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public record Tabla(String grupo, @JsonInclude(JsonInclude.Include.NON_EMPTY) List<TablaEquipo> equipos) {
}
