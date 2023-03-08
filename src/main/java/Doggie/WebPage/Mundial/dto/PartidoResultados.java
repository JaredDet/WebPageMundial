package Doggie.WebPage.Mundial.dto;

import java.util.List;

public record PartidoResultados(List<EquipoResultados> equipos, String fase, String fecha) {
}
