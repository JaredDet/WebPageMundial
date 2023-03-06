package Doggie.WebPage.Mundial.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private Equipo equipo;
    @OneToOne
    private Posicion posicionTitular;
    private int dorsal;
    @Column(unique = true)
    private String nombre;
    private int edad;
}

