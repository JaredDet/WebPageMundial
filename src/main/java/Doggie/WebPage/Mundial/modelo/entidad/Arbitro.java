package Doggie.WebPage.Mundial.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "arbitros")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Arbitro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "arbitroId")
    private Long arbitroId;
    private String nombre;

    @JsonBackReference
    @ManyToOne
    private Pais pais;

    public String pais() {
        return pais.getNombre();
    }
}
