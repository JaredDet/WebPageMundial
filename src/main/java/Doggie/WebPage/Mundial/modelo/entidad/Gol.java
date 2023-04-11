package Doggie.WebPage.Mundial.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
}
