package Doggie.WebPage.Mundial.modelo.entidad;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Period;
import java.util.List;

@Entity
@Table(name = "jugadorEnCancha")
@Getter
@Setter
public class RelJugadoresPartidos {

    @Embeddable
    @Getter
    @Setter
    @EqualsAndHashCode
    static class RelJugadoresPartidosPK implements Serializable {

        @Column(name = "jugadorId")
        Long jugadorId;

        @Column(name = "partidoId")
        Long partidoId;
    }

    @EmbeddedId
    private RelJugadoresPartidosPK relJugadoresPartidosPK;

    @ManyToOne
    @MapsId("jugadorId")
    private Jugador jugador;

    @ManyToOne
    @MapsId("partidoId")
    private Partido partido;

    @OneToOne
    private Posicion posicionActual;

    @OneToMany(mappedBy = "amonestado")
    private List<Tarjeta> tarjetas;

    public int getEdadPartido() {
        return Period.between(jugador.getFechaNacimiento(), partido.getFecha()).getYears();
    }
}
