package Doggie.WebPage.Mundial.modelo.entidad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "jugadores")
@Getter
@Setter
@ToString
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jugadorId")
    private Long jugadorId;
    private String nombre;
    private int dorsal;

    @ManyToOne
    private Equipo equipo;

    @OneToMany(mappedBy = "jugador")
    private List<Gol> goles;

    @OneToMany(mappedBy = "jugador")
    private List<Tarjeta> tarjetas;

    @OneToMany(mappedBy = "jugador")
    private List<Convocado> convocaciones;

    @OneToMany(mappedBy = "jugador")
    private List<Cambio> historialSustituciones;
}

