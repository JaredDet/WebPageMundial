package Doggie.WebPage.Mundial.modelo.entidad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "convocados")
@Getter
@Setter
@ToString
public class Convocado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "convocadoId")
    private Long convocadoId;

    @ManyToOne
    private Jugador jugador;
    @ManyToOne
    private Partido partido;

    @OneToOne
    private Posicion posicion;

    @Column(columnDefinition = "boolean default false")
    private boolean esCapitan;

    @Column(columnDefinition = "boolean default false")
    private boolean esJugadorPartido;

    @Column(columnDefinition = "boolean default true")
    private boolean esTitular;
}
