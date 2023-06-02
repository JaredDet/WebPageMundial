package Doggie.WebPage.Mundial.modelo.entidad;

import Doggie.WebPage.Mundial.modelo.Color;
import Doggie.WebPage.Mundial.modelo.Resultado;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
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
    @ManyToOne(fetch = FetchType.EAGER)
    private Equipo equipo;

    @Column(columnDefinition = "boolean default false")
    private boolean esLocal;

    @OneToOne
    private Formacion formacion;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Estadistica estadisticas;

    public String formacion() {
        return formacion.getEstrategia();
    }

    public String posicionLaterales() {
        return formacion.posicionLaterales();
    }

    public String getNombre() {
        return equipo.getNombre();
    }

    public String getGrupo() {
        return equipo.grupo();
    }

    public String getCodigo() {
        return equipo.codigo();
    }

    public List<Jugador> jugadores() {
        return partido.jugadoresEquipo(equipo);
    }

    public List<Jugador> getAnotadores() {
        return jugadores().stream()
                .filter(jugador -> jugador.anotoGolesReglamentariosEnPartido(partido))
                .toList();
    }

    public List<Jugador> getAnotadoresTandaPenales() {
        return jugadores().stream()
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

    public Date fechaJugada() {
        return partido.getFecha();
    }

    public String resultado() {

        if(gana()) return Resultado.GANA.name();

        if(pierde()) return Resultado.PIERDE.name();

        if(empata()) return Resultado.EMPATA.name();

        return Resultado.NO_JUGADO.name();
    }
}
