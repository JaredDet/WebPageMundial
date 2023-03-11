package Doggie.WebPage.Mundial.dto;

import java.time.LocalDate;
import java.util.List;

public record EstadisticasPartido(List<EstadisticaEquipo> equipos, LocalDate fecha, String arbitro, String estadio, int duracionMinutos,
                                  String fase) {
}
