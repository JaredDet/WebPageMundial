package Doggie.WebPage.Mundial.dto;

import java.util.Date;
import java.util.List;

public record Ronda(List<MarcadorEquipo> marcador, String fase, Date fecha) {
}
