package Doggie.WebPage.Mundial;

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
    private Long partidoId;
    @OneToOne
    private Equipo equipoLocal;
    @OneToOne
    private Equipo equipoVisitante;

    @OneToOne
    private Estadistica estadisticasLocal;
    @OneToOne
    private Estadistica estadisticasVisitante;

    private LocalDate fecha;
    private int duracion;

    @OneToOne
    private Fase fase;

    @OneToMany(mappedBy = "partido")
    private List<JugadorEnCancha> jugadores;

    @OneToMany(mappedBy = "partido")
    private List<Sustitucion> sustituciones;

    @OneToOne
    private Estadio estadio;

    @OneToOne
    private Arbitro arbitro;
}
