package Doggie.WebPage.Mundial.dto;

import java.util.List;

public record EquipoPeticion(String nombre, List<JugadorPeticion> jugadores, EstadisticaPeticion estadistica) {

}
