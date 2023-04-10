package Doggie.WebPage.Mundial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

public record Ronda(@JsonInclude(JsonInclude.Include.NON_EMPTY) List<MarcadorEquipo> marcador,
                    @JsonInclude(JsonInclude.Include.NON_EMPTY) List<MarcadorEquipo> penales, String fase, Date fecha) {
}
