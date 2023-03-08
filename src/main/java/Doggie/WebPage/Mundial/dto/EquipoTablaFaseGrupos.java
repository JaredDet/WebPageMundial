package Doggie.WebPage.Mundial.dto;

public record EquipoTablaFaseGrupos(String nombre, int partidosJugados, int ganados, int empates,
                                    int perdidos, int golesAFavor, int golesEnContra, int diferenciaGoles,
                                    int puntos) implements Comparable<EquipoTablaFaseGrupos> {
    @Override
    public int compareTo(EquipoTablaFaseGrupos o) {

        if (this.puntos != o.puntos) {
            return Integer.compare(o.puntos(), this.puntos());
        }
        if (this.ganados != o.ganados) {
            return Integer.compare(o.ganados(), this.ganados());
        }

        if (this.diferenciaGoles != o.diferenciaGoles) {
            return Integer.compare(o.diferenciaGoles(), this.diferenciaGoles());
        }

        if (this.golesAFavor() != o.golesAFavor()) {
            return Integer.compare(o.golesAFavor(), this.golesAFavor());
        }

        return o.nombre.compareTo(this.nombre());
    }
}
