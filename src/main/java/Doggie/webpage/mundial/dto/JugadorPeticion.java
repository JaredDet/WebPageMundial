package Doggie.WebPage.Mundial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
public class JugadorPeticion {

    private String nombre;

    private boolean esTitular;

    private boolean esCapitan;

    private boolean esJugadorPartido;

    private String posicion;
    private String lado;
    private List<GolPeticion> goles;
    private SustitucionPeticion sustitucion;
    private List<TarjetaPeticion> tarjetas;

    public JugadorPeticion() {
        this.esTitular = true;
        this.esCapitan = false;
        this.esJugadorPartido = false;
        this.lado = "CENTRAL";
    }
}
