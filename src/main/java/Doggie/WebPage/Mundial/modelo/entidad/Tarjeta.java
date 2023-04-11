package Doggie.WebPage.Mundial.modelo.entidad;

import Doggie.WebPage.Mundial.modelo.Color;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tarjetas")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Tarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tarjetaId")
    private Long tarjetaId;
    private int minuto;

    @ManyToOne
    @JsonManagedReference
    private Jugador jugador;

    @Enumerated(EnumType.STRING)
    private Color color;

    @ManyToOne
    @JsonBackReference
    private Partido partido;
}
