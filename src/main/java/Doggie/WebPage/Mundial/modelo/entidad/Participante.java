package Doggie.WebPage.Mundial.modelo.entidad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "participantes")
@Getter
@Setter
@ToString
public class Participante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participanteId")
    private Long participanteId;

    @ManyToOne
    private Partido partido;

    @ManyToOne
    private Equipo equipo;

    @Column(columnDefinition = "boolean default false")
    private boolean esLocal;
}
