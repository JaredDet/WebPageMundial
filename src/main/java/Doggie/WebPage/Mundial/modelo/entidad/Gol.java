package Doggie.WebPage.Mundial.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "goles")
@Getter
@Setter
@ToString
@EqualsAndHashCode

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

    @JsonBackReference
    @ManyToOne
    private Partido partido;

    @JsonBackReference
    @ManyToOne
    private Jugador jugador;

    /**
     * Devuelve true si el gol no fue convertido, es decir, si no entró en la portería.
     *
     * @return true si el gol no entró, false si el gol fue convertido.
     */

    public boolean noEntro() {
        return !entro;
    }

    public boolean esGolReglamentario() {
        return minuto != null;
    }

    public boolean esGolTandaPenales() {
        return !esGolReglamentario() && penal;
    }
}
