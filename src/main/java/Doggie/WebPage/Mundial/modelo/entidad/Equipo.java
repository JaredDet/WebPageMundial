package Doggie.WebPage.Mundial.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "equipo")
@Getter
@Setter
public class Equipo {

    @Id
    private Long equipoId;
    @Column(unique = true)
    private String nombre;
    @Column(unique = true)
    private String abreviatura;
    @Column(unique = true)
    private String bandera;
    @OneToOne
    private Fase faseActual;
    @ManyToOne
    private Grupo grupo;
    @OneToMany(mappedBy = "jugadorId")
    @JsonManagedReference
    private List<Jugador> jugadores;
    @OneToMany(mappedBy = "estadisticaId")
    private List<Estadistica> estadisticas;
}
