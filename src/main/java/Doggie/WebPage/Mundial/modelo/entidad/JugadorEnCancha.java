package Doggie.WebPage.Mundial.modelo.entidad;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "jugadorEnCancha")
@Getter
@Setter
public class JugadorEnCancha {

    @Embeddable
    @Getter
    @Setter
    @EqualsAndHashCode
    static class JugadorEnCanchaPK implements Serializable {

        @Column(name = "jugadorId")
        Long jugadorId;

        @Column(name = "partidoId")
        Long partidoId;
    }

    @EmbeddedId
    private JugadorEnCanchaPK jugadorEnCanchaPK;

    @OneToOne
    @MapsId("jugadorId")
    private Jugador jugador;

    @ManyToOne
    @MapsId("partidoId")
    private Partido partido;

    @OneToOne
    private Posicion posicionActual;

    @OneToMany(mappedBy = "autor")
    private List<Gol> goles;

    @OneToMany(mappedBy = "amonestado")
    private List<Tarjeta> tarjetas;
}
