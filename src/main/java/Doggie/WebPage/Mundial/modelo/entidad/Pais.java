package Doggie.WebPage.Mundial.modelo.entidad;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "paises")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Pais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paisId")
    private Long paisId;
    private String nombre;
    private String url;
}
