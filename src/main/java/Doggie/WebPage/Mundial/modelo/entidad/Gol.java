package Doggie.WebPage.Mundial.modelo.entidad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "goles")
@Getter
@Setter
@ToString


public class Gol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "golId")
    private Long golId;

    private Integer minuto;

    @Column(columnDefinition = "boolean default false")
    private boolean penal;

    @Column(columnDefinition = "boolean default true")
    private boolean entro;

    @ManyToOne
    private Partido partido;

    @ManyToOne
    private Jugador jugador;
}
