package Doggie.WebPage.Mundial.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "equipoEnPartido")
@Getter
@Setter
public class EquipoEnPartido {

    @Embeddable
    @Getter
    @Setter
    @EqualsAndHashCode
    public static class EquipoEnPartidoPK implements Serializable {

        @Column(name = "partidoId")
        Long partidoId;

        @Column(name = "equipoId")
        Long equipoId;
    }

    @EmbeddedId
    private EquipoEnPartidoPK equipoEnPartidoPK;

    @ManyToOne
    @MapsId("partidoId")
    @JsonBackReference
    private Partido partido;

    @ManyToOne
    @MapsId("equipoId")
    @JsonManagedReference
    private Equipo equipo;

    @OneToMany(mappedBy = "equipo")
    private List<JugadorEnPartido> jugadores;

    @OneToOne
    private Estadistica estadistica;

    @OneToOne
    private Formacion formacion;
}
