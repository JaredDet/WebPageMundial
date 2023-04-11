package Doggie.WebPage.Mundial.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cambios")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Cambio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cambioId")
    private Long cambioId;

    @JsonBackReference
    @ManyToOne
    private Sustitucion sustitucion;

    @JsonBackReference
    @ManyToOne
    private Jugador jugador;

    @Column(columnDefinition = "boolean default true")
    private boolean entra;
}
