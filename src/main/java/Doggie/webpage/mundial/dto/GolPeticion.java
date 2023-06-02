package Doggie.WebPage.Mundial.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GolPeticion {

    private int minuto;
    private boolean penal;
    private boolean entro;

    public GolPeticion() {
        penal = false;
        entro = false;
    }
}
