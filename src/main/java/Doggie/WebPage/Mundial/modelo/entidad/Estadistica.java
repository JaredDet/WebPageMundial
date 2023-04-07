package Doggie.WebPage.Mundial.modelo.entidad;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "estadisticas")
@Getter
@Setter
@ToString
public class Estadistica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long estadisticaId;

    private Integer remates;
    private Integer rematesAlArco;
    private Integer posesion;
    private Integer pases;
    private Integer precisionPases;
    private Integer faltas;
    private Integer posicionesAdelantadas;
    private Integer tirosEsquina;
}
