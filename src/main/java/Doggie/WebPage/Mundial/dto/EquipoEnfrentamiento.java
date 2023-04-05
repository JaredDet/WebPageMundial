package Doggie.WebPage.Mundial.dto;

import java.util.List;

public record EquipoEnfrentamiento(String nombre, List<JugadorEnfrentamiento> jugadores, boolean esLocal) {

}
