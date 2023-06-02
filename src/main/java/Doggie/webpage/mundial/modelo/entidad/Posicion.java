package Doggie.WebPage.Mundial.modelo.entidad;

import Doggie.WebPage.Mundial.modelo.Lado;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "posiciones")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Posicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "posicionId")
    private Long posicionId;

    private String nombre;

    @OneToOne
    private Abreviatura abreviatura;

    @Enumerated(EnumType.STRING)
    private Lado lado;

    public String abreviatura() {
        return abreviatura.getNombre();
    }

    public String lado() {
        return lado.name();
    }
}
