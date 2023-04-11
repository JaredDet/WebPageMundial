package Doggie.WebPage.Mundial.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "equipos")
@Getter
@Setter
@ToString(exclude = {"jugadores", "partidosParticipados"})
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipoId")
    private Long equipoId;

    @JsonManagedReference
    @OneToMany(mappedBy = "equipo")
    private List<Jugador> jugadores;

    @JsonManagedReference
    @OneToMany(mappedBy = "equipo")
    private List<Participante> partidosParticipados;

    @JsonBackReference
    @OneToOne
    private Tecnico tecnico;

    @JsonBackReference
    @OneToOne
    private Pais pais;

    @JsonBackReference
    @ManyToOne
    private Grupo grupo;

    public boolean gana(Partido partido) {
        return partido.golesEquipo(this) > partido.golesRival(this);
    }

    public boolean pierde(Partido partido) {
        return partido.golesEquipo(this) < partido.golesRival(this);
    }

    public boolean empata(Partido partido) {
        return partido.golesEquipo(this) == partido.golesRival(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipo equipo = (Equipo) o;
        return Objects.equals(getEquipoId(), equipo.getEquipoId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEquipoId());
    }
}
