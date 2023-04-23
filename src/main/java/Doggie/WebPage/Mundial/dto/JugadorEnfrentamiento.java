package Doggie.WebPage.Mundial.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record JugadorEnfrentamiento(String nombre, int dorsal,
                                    @JsonInclude(JsonInclude.Include.NON_NULL) String posicion, boolean esCapitan,
                                    boolean esJugadorPartido, boolean esTitular,
                                    @JsonInclude(JsonInclude.Include.NON_EMPTY) List<DatosTarjeta> tarjetas,
                                    DatosSustitucion sustitucion) implements Comparable<JugadorEnfrentamiento> {

    public static final Map<String, Integer> VALORES_POSICIONES = new HashMap<>();

    static {
        VALORES_POSICIONES.put("POR", 1);
        VALORES_POSICIONES.put("DEF", 2);
        VALORES_POSICIONES.put("MED", 3);
        VALORES_POSICIONES.put("DEL", 4);
    }


    @Override
    public int compareTo(@NotNull JugadorEnfrentamiento o) {

        if (posicion == null) {
            return 1;
        }
        if (o.posicion == null) {
            return -1;
        }

        var valorPosicionActual = VALORES_POSICIONES.get(posicion);
        var valorPosicionOtroJugador = VALORES_POSICIONES.get(o.posicion);
        return Integer.compare(valorPosicionActual, valorPosicionOtroJugador);
    }
}