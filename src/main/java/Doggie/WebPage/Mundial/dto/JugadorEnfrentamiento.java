package Doggie.WebPage.Mundial.dto;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record JugadorEnfrentamiento(String nombre, int dorsal, String posicion, boolean esCapitan,
                                    boolean esJugadorPartido, boolean esTitular, List<DatosTarjeta> tarjetas,
                                    DatosSustitucion sustitucion) implements Comparable<JugadorEnfrentamiento> {

    public static final Map<String, Integer> VALORES_POSICIONES = new HashMap<>();

    static {
        VALORES_POSICIONES.put("POR", 4);
        VALORES_POSICIONES.put("DEF", 3);
        VALORES_POSICIONES.put("MED", 2);
        VALORES_POSICIONES.put("DEL", 1);
    }


    @Override
    public int compareTo(JugadorEnfrentamiento o) {

        var valorPosicionActual = VALORES_POSICIONES.get(posicion);
        var valorPosicionOtroJugador = VALORES_POSICIONES.get(o.posicion);
        return Integer.compare(valorPosicionOtroJugador, valorPosicionActual);
    }
}
