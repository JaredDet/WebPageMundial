package Doggie.WebPage.Mundial.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "relPartidosEquipos")
@Getter
@Setter
public class RelPartidosEquipos {

    @Embeddable
    @Getter
    @Setter
    @EqualsAndHashCode
    static class RelPartidosEquiposPK implements Serializable {

        @Column(name = "partidoId")
        Long partidoId;

        @Column(name = "equipoId")
        Long equipoId;
    }

    @EmbeddedId
    private RelPartidosEquiposPK relPartidosEquiposPK;

    @ManyToOne
    @MapsId("partidoId")
    @JsonBackReference
    private Partido partido;

    @ManyToOne
    @MapsId("equipoId")
    @JsonManagedReference
    private Equipo equipo;

    @OneToOne
    private Estadistica estadistica;
}
