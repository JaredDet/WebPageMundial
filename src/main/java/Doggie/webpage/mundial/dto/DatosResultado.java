package Doggie.WebPage.Mundial.dto;

import java.util.Date;

public record DatosResultado(String resultado, Date fecha) implements Comparable<DatosResultado> {

    @Override
    public int compareTo(DatosResultado otro) {
        return this.fecha.compareTo(otro.fecha());
    }
}

