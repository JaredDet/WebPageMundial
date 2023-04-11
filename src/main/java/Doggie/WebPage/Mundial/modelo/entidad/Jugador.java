package Doggie.WebPage.Mundial.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "jugadores")
@Getter
@Setter
@ToString(exclude = {"goles", "tarjetas", "convocaciones", "historialSustituciones"})
@EqualsAndHashCode

public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jugadorId")
    private Long jugadorId;
    private String nombre;
    private int dorsal;

    @JsonBackReference
    @ManyToOne
    private Equipo equipo;

    @JsonManagedReference
    @OneToMany(mappedBy = "jugador")
    private List<Gol> goles;

    @JsonManagedReference
    @OneToMany(mappedBy = "jugador")
    private List<Tarjeta> tarjetas;

    @JsonManagedReference
    @OneToMany(mappedBy = "jugador")
    private List<Convocado> convocaciones;

    @JsonManagedReference
    @OneToMany(mappedBy = "jugador")
    private List<Cambio> historialSustituciones;
}


