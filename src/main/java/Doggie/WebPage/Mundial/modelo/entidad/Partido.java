package Doggie.WebPage.Mundial.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Entity
@Table(name = "partidos")
@Getter
@Setter
@ToString(exclude = {"equiposParticipantes", "convocados", "goles", "sustituciones"})
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partidoId")
    private Long partidoId;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Temporal(TemporalType.TIME)
    private Date hora;

    @Column(columnDefinition = "boolean default false")
    private boolean tandaPenales;

    @JsonManagedReference
    @OneToMany(mappedBy = "partido")
    private List<Participante> equiposParticipantes;

    @JsonManagedReference
    @OneToMany(mappedBy = "partido")
    private List<Convocado> convocados;

    @JsonManagedReference
    @OneToMany(mappedBy = "partido")
    private List<Gol> goles;

    @JsonManagedReference
    @OneToMany(mappedBy = "partido")
    private List<Sustitucion> sustituciones;

    @JsonBackReference
    @OneToOne
    private Estadio estadio;

    @JsonBackReference
    @OneToOne
    private Arbitro arbitro;

    @JsonBackReference
    @ManyToOne
    private Fase fase;

    public int golesEquipo(Equipo equipo) {
        var listaGoles = listaGolesEquipo(equipo);
        return listaGoles.size() - penales(equipo, Gol::isEntro) - penales(equipo, gol -> !gol.isEntro() && gol.isPenal());
    }

    public int penales(Equipo equipo) {
        return penales(equipo, Gol::isEntro);
    }

    private int penales(Equipo equipo, Predicate<Gol> predicado) {
        var listaGoles = listaGolesEquipo(equipo);
        return (int) listaGoles.stream()
                .filter(gol -> gol.getMinuto() == null && gol.isPenal() && predicado.test(gol))
                .count();
    }

    public List<Gol> listaGolesEquipo(Equipo equipo) {
        return goles.stream()
                .filter(gol -> gol.getJugador().getEquipo().getEquipoId().equals(equipo.getEquipoId()))
                .toList();
    }

    public int golesRival(Equipo equipo) {
        var rival = getRival(equipo);
        return golesEquipo(rival);
    }

    private Equipo getRival(Equipo equipo) {
        return equiposParticipantes.stream()
                .filter(participante -> participante.getEquipo() != equipo)
                .findFirst().get()
                .getEquipo();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partido partido = (Partido) o;
        return Objects.equals(partidoId, partido.partidoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partidoId);
    }
}
