package Doggie.WebPage.Mundial.modelo.entidad;

import Doggie.WebPage.Mundial.modelo.Color;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "participantes")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Participante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participanteId")
    private Long participanteId;

    @JsonBackReference
    @ManyToOne
    private Partido partido;

    @JsonBackReference
    @ManyToOne
    private Equipo equipo;

    @Column(columnDefinition = "boolean default false")
    private boolean esLocal;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Estadistica estadisticas;

    public String getNombre() {
        return equipo.getNombre();
    }

    public String grupo() {
        return equipo.grupo();
    }

    public List<Jugador> getJugadores() {
        return partido.jugadoresEquipo(this.equipo);
    }

    public List<Jugador> getAnotadores() {
        return getJugadores().stream()
                .filter(jugador -> jugador.anotoGolesReglamentariosEnPartido(partido))
                .toList();
    }

    public List<Jugador> getAnotadoresTandaPenales() {
        return getJugadores().stream()
                .filter(jugador -> jugador.anotoPenalesEnTandaPenal(partido))
                .toList();
    }

    public int goles() {
        return partido.golesEquipo(this.equipo);
    }

    public int getTarjetasAmarillas() {
        return partido.tarjetasEquipo(this.equipo, Color.AMARILLA);
    }

    public int getTarjetasRojas() {
        return partido.tarjetasEquipo(this.equipo, Color.ROJA);
    }

    public int golesEnContra() {
        return partido.golesRival(this.equipo);
    }

    public int penales() {
        return partido.penales(this.equipo);
    }

    /**
     * Determina si el equipo ha ganado el partido.
     *
     * @return true si el equipo ha ganado el partido, false en caso contrario.
     */

    public boolean gana() {
        return goles() > golesEnContra();
    }

    /**
     * Determina si el equipo ha perdido el partido.
     *
     * @return true si el equipo ha perdido, false en caso contrario.
     */

    public boolean pierde() {
        return goles() < golesEnContra();
    }

    /**
     * Determina si el equipo empató el partido o no.
     *
     * @return true si el equipo empató el partido, false en caso contrario.
     */

    public boolean empata() {
        return goles() == golesEnContra();
    }

    public Tecnico tecnico() {
        return equipo.getTecnico();
    }
}
