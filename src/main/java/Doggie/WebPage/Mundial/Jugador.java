package Doggie.WebPage.Mundial;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "jugador")
@Getter
@Setter
public class Jugador {

    @Id
    @Column(name = "jugadorId")
    private Long jugadorId;
    @ManyToOne
    private Equipo equipo;
    @OneToOne
    private Posicion posicionTitular;
    private int dorsal;
    private String nombre;
    private int edad;
}

