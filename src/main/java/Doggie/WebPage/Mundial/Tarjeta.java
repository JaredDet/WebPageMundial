package Doggie.WebPage.Mundial;

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
    private JugadorEnCancha amonestado;

    private int minuto;

    @Enumerated(EnumType.STRING)
    private Color colorTarjeta;
}
