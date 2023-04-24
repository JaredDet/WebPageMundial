package Doggie.WebPage.Mundial.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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

    public int ganados() {
        return (int) partidosParticipados
                .stream()
                .filter(Participante::gana)
                .count();
    }

    public int perdidos() {
        return (int) partidosParticipados
                .stream()
                .filter(Participante::pierde)
                .count();
    }

    public int empatados() {
        return (int) partidosParticipados
                .stream()
                .filter(Participante::empata)
                .count();
    }

    public int goles() {
        return partidosParticipados
                .stream()
                .mapToInt(Participante::goles)
                .sum();
    }

    public int golesEnContra() {
        return partidosParticipados
                .stream()
                .mapToInt(Participante::golesEnContra)
                .sum();
    }
    public String getNombre() {
        return pais.getNombre();
    }

    public String grupo() {
        return grupo.getNombre();
    }
}
