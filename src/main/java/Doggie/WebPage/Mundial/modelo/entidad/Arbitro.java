package Doggie.WebPage.Mundial.modelo.entidad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "arbitros")
@Getter
@Setter
@ToString
public class Arbitro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "arbitroId")
    private Long arbitroId;
    private String nombre;
    @ManyToOne
    private Pais pais;
}
