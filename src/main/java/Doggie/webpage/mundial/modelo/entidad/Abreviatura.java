package Doggie.WebPage.Mundial.modelo.entidad;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "abreviaturas")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Abreviatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "abreviaturaId")
    private Long abreviaturaId;
    private String nombre;
}
