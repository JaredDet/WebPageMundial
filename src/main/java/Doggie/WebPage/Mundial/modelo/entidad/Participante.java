package Doggie.WebPage.Mundial.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    @OneToOne
    private Estadistica estadisticas;
}
