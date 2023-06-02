package Doggie.WebPage.Mundial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public record Plantilla(String nombre, String bandera, AlineacionTitular titulares,
                        List<JugadorPlantilla> banca, @JsonInclude(JsonInclude.Include.NON_DEFAULT) Boolean esLocal,
                        @JsonInclude(JsonInclude.Include.NON_DEFAULT) Boolean esVisita, DatosTecnico tecnico, String formacion, @JsonInclude(JsonInclude.Include.NON_DEFAULT) String posicionLaterales) {
}
