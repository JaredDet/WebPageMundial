package Doggie.WebPage.Mundial.dto;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public record TablaEquipo(String nombre, String bandera, int partidosJugados, int ganados, int perdidos, int empatados, int golesAFavor,
                          int golesEnContra, int diferenciaGoles,
                          int puntos, List<DatosResultado> resultados) implements Comparable<TablaEquipo> {
    @Override
    public int compareTo(@NotNull TablaEquipo o) {
        return Comparator.comparingInt(TablaEquipo::puntos)
                .thenComparingInt(TablaEquipo::ganados)
                .thenComparingInt(TablaEquipo::diferenciaGoles)
                .thenComparingInt(TablaEquipo::golesAFavor)
                .thenComparing(TablaEquipo::nombre)
                .reversed()
                .compare(this, o);
    }
}
