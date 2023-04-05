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

    private int minuto;

    @ManyToOne
    private Partido partido;

    @ManyToOne
    private Jugador jugador;
}
