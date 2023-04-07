package Doggie.WebPage.Mundial.dto;


import java.util.List;

public record JugadorEnfrentamiento(String nombre, int dorsal, String posicion, boolean esCapitan,
                                    boolean esJugadorPartido, boolean esTitular, List<DatosTarjeta> tarjetas,
                                    DatosSustitucion sustitucion) {
}
