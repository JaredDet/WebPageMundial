package Doggie.WebPage.Mundial.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tecnicos")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Tecnico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tecnicoId")
    private Long tecnicoId;

    private String nombre;

    @JsonBackReference
    @ManyToOne
    private Pais pais;
}
