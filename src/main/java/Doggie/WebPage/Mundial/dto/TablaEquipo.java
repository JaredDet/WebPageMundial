package Doggie.WebPage.Mundial.dto;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public record TablaEquipo(String nombre, int partidosJugados, int ganados, int perdidos, int empatados, int golesAFavor,
                          int golesEnContra, int diferenciaGoles,
                          int puntos) implements Comparable<TablaEquipo> {
    @Override
    public int compareTo(@NotNull TablaEquipo o) {

        return Comparator.comparingInt(TablaEquipo::puntos).reversed()
                .thenComparingInt(TablaEquipo::ganados).reversed()
                .thenComparingInt(TablaEquipo::diferenciaGoles).reversed()
                .thenComparingInt(TablaEquipo::golesAFavor).reversed()
                .thenComparing(TablaEquipo::nombre)
                .compare(this, o);
    }
}
