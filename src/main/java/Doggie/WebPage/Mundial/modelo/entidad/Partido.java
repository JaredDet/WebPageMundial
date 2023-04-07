package Doggie.WebPage.Mundial.modelo.entidad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "partidos")
@Getter
@Setter
@ToString
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partidoId")
    private Long partidoId;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Temporal(TemporalType.TIME)
    private Date hora;


    @OneToMany(mappedBy = "partido")
    private List<Participante> equiposParticipantes;

    @OneToMany(mappedBy = "partido")
    private List<Convocado> convocados;

    @OneToMany(mappedBy = "partido")
    private List<Gol> goles;

    @OneToMany(mappedBy = "partido")
    private List<Sustitucion> sustituciones;

    @OneToOne
    private Estadio estadio;

    @OneToOne
    private Arbitro arbitro;

    @ManyToOne
    private Fase fase;
}
