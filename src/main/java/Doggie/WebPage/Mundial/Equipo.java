package Doggie.WebPage.Mundial;

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
    private String nombre;
    @OneToOne
    private Fase faseActual;
    @ManyToOne
    private Grupo grupo;
    @OneToMany(mappedBy = "jugadorId")
    private List<Jugador> jugadores;
    @OneToMany(mappedBy = "estadisticaId")
    private List<Estadistica> estadisticas;
}
