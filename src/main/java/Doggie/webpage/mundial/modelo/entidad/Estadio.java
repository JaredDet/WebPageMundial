package Doggie.WebPage.Mundial.modelo.entidad;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "estadios")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Estadio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estadioId")
    private Long estadioId;
    private String nombre;
}
