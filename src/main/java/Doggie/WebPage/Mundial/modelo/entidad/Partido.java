package Doggie.WebPage.Mundial.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "partido")
@Getter
@Setter
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partidoId;

    private LocalDate fecha;

    private Integer duracion;

    @OneToOne
    private Fase fase;

    @JsonManagedReference
    @OneToMany(mappedBy = "partido", fetch = FetchType.EAGER)
    private List<RelPartidosEquipos> relPartidosEquipos;

    @OneToMany(mappedBy = "partido")
    private List<RelJugadoresPartidos> relJugadoresPartidos;

    @OneToMany(mappedBy = "partido")
    private List<Sustitucion> sustituciones;

    @OneToOne
    private Estadio estadio;

    @OneToOne
    private Arbitro arbitro;
}
