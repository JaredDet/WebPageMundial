package Doggie.WebPage.Mundial.modelo.entidad;

import Doggie.WebPage.Mundial.modelo.Color;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tarjeta")
@Getter
@Setter
public class Tarjeta {

    @Id
    private Long tarjetaId;

    @ManyToOne
    private Estadistica estadistica;

    @ManyToOne
    private RelJugadoresPartidos amonestado;

    private int minuto;

    @Enumerated(EnumType.STRING)
    private Color colorTarjeta;
}
