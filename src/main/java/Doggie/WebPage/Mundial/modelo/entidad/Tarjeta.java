package Doggie.WebPage.Mundial.modelo.entidad;

import Doggie.WebPage.Mundial.modelo.Color;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tarjetas")
@Getter
@Setter
@ToString
public class Tarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tarjetaId")
    private Long tarjetaId;
    private int minuto;

    @ManyToOne
    private Jugador jugador;

    @Enumerated(EnumType.STRING)
    private Color color;

    @ManyToOne
    private Partido partido;
}
