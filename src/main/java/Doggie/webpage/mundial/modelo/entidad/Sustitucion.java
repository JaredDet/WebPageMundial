package Doggie.WebPage.Mundial.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "sustituciones")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Sustitucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sustitucionId")
    private Long sustitucionId;

    @JsonBackReference
    @ManyToOne
    private Partido partido;
    private int minuto;
}
