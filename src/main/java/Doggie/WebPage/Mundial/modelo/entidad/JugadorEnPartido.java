package Doggie.WebPage.Mundial.modelo.entidad;

import Doggie.WebPage.Mundial.modelo.EstadoJugador;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Period;
import java.util.List;

@Entity
@Table(name = "jugadorEnPartido")
@Getter
@Setter
public class JugadorEnPartido {

    @Embeddable
    @Getter
    @Setter
    @EqualsAndHashCode
    public static class JugadorEnPartidoPK implements Serializable {

        @Column(name = "jugadorId")
        Long jugadorId;

        @Column(name = "equipoEnPartidoPK")
        EquipoEnPartido.EquipoEnPartidoPK equipoEnPartidoPK;
    }

    @EmbeddedId
    private JugadorEnPartidoPK jugadorEnPartidoPK;

    @ManyToOne
    @MapsId("jugadorId")
    private Jugador jugador;

    @ManyToOne
    @MapsId("equipoEnPartidoPK")
    private EquipoEnPartido equipo;

    @OneToOne
    private Posicion posicionActual;

    @OneToMany(mappedBy = "amonestado")
    private List<Tarjeta> tarjetas;

    @Enumerated(EnumType.STRING)
    private EstadoJugador estadoJugador;

    public int getEdadPartido() {
        return Period.between(jugador.getFechaNacimiento(), equipo.getPartido().getFecha()).getYears();
    }
}
