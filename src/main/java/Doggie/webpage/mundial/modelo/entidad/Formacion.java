package Doggie.WebPage.Mundial.modelo.entidad;

import Doggie.WebPage.Mundial.modelo.PosicionLaterales;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "formaciones")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Formacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "formacionId")
    private Long formacionId;
    private String estrategia;

    @Enumerated(EnumType.STRING)
    private PosicionLaterales posicionLaterales;

    public String posicionLaterales() {
        return posicionLaterales.name();
    }
}
