package Doggie.WebPage.Mundial.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.jetbrains.annotations.NotNull;

public record JugadorEnfrentamiento(String nombre, int dorsal,
                                    @JsonInclude(JsonInclude.Include.NON_NULL) String posicion, String abreviatura,
                                    String lado,
                                    @JsonInclude(JsonInclude.Include.NON_DEFAULT) Boolean esCapitan,
                                    @JsonInclude(JsonInclude.Include.NON_DEFAULT) Boolean esJugadorPartido,
                                    @JsonInclude(JsonInclude.Include.NON_DEFAULT) Boolean esTitular,
                                    @JsonInclude(JsonInclude.Include.NON_DEFAULT) Boolean tarjetaAmarilla,
                                    @JsonInclude(JsonInclude.Include.NON_DEFAULT) Boolean tarjetaRoja,
                                    @JsonInclude(JsonInclude.Include.NON_DEFAULT) Boolean gol,
                                    @JsonInclude(JsonInclude.Include.NON_DEFAULT) Boolean entro)
        implements Comparable<JugadorEnfrentamiento> {

    @Override
    public int compareTo(@NotNull JugadorEnfrentamiento o) {
        if (this.lado.equals(o.lado)) {
            return 0;
        } else if (this.lado.equals("IZQUIERDO")) {
            return -1;
        } else if (this.lado.equals("CENTRAL") && o.lado.equals("DERECHO")) {
            return -1;
        }
        return 1;
    }
}