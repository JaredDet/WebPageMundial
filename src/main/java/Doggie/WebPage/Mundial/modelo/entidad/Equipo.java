package Doggie.WebPage.Mundial.modelo.entidad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "equipos")
@Getter
@Setter
@ToString
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipoId")
    private Long equipoId;

    @OneToMany(mappedBy = "equipo")
    private List<Jugador> jugadores;

    @OneToMany(mappedBy = "equipo")
    private List<Participante> partidosParticipados;

    @OneToOne
    private Tecnico tecnico;

    @OneToOne
    private Pais pais;

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
}
