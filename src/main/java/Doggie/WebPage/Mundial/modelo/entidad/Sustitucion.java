package Doggie.WebPage.Mundial.modelo.entidad;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "sustitucion")
@Getter
@Setter
public class Sustitucion {

    @Embeddable
    @Getter
    @Setter
    @EqualsAndHashCode
    static class SustitucionPK implements Serializable {

        @Column(name = "jugadorSalienteId")
        Long jugadorSalienteId;

        @Column(name = "jugadorEntranteId")
        Long jugadorEntranteId;

        @Column(name = "partidoId")
        Long partidoId;
    }

    @EmbeddedId
    private SustitucionPK sustitucionPK;

    @OneToOne
    @MapsId("jugadorSalienteId")
    @JoinColumn(name = "jugadorSalienteId")
    private Jugador jugadorSaliente;

    @OneToOne
    @MapsId("jugadorEntranteId")
    @JoinColumn(name = "jugadorEntranteId")
    private Jugador jugadorEntrante;

    @ManyToOne
    @MapsId("partidoId")
    @JoinColumn(name = "partidoId")
    private Partido partido;

    private int minutoCambio;
}
