package Doggie.WebPage.Mundial.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
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
    private List<GolPeticion> goles;
    private SustitucionPeticion sustitucion;
    private List<TarjetaPeticion> tarjetas;

    public JugadorPeticion() {
        this.esTitular = true;
        this.esCapitan = false;
        this.esJugadorPartido = false;
    }
}
