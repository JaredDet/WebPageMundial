package Doggie.WebPage.Mundial.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "convocados")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Convocado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "convocadoId")
    private Long convocadoId;

    @JsonManagedReference
    @ManyToOne
    private Jugador jugador;

    @JsonBackReference
    @ManyToOne
    private Partido partido;

    @JsonManagedReference
    @OneToOne
    private Posicion posicion;

    @Column(columnDefinition = "boolean default false")
    private boolean esCapitan;

    @Column(columnDefinition = "boolean default false")
    private boolean esJugadorPartido;

    @Column(columnDefinition = "boolean default true")
    private boolean esTitular;
}
