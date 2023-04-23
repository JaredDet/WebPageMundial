package Doggie.WebPage.Mundial.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SustitucionPeticion {


    private int minuto;

    private boolean entro;

    public SustitucionPeticion() {
        this.entro = true;
    }
}
