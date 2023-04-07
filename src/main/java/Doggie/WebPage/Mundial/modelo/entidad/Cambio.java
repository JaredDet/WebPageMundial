package Doggie.WebPage.Mundial.modelo.entidad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cambios")
@Getter
@Setter
@ToString
public class Cambio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cambioId")
    private Long cambioId;

    @ManyToOne
    private Sustitucion sustitucion;
    @ManyToOne
    private Jugador jugador;

    @Column(columnDefinition = "boolean default true")
    private boolean entra;
}
