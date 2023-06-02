package Doggie.WebPage.Mundial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public record DatosSustitucion(@JsonInclude(JsonInclude.Include.NON_DEFAULT) Boolean entra,
                               @JsonInclude(JsonInclude.Include.NON_DEFAULT) Boolean sale, int minuto) {
}
