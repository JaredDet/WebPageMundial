package Doggie.WebPage.Mundial.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Entity
@Table(name = "jugador")
@Getter
@Setter
public class Jugador {

    @Id
    @Column(name = "jugadorId")
    private Long jugadorId;
    @ManyToOne
    @JsonBackReference
    private Equipo equipo;
    @OneToOne
    private Posicion posicionTitular;

    @OneToMany(mappedBy = "jugador")
    private List<JugadorEnPartido> juegosJugados;

    private int dorsal;
    @Column(unique = true)
    private String nombre;
    private LocalDate fechaNacimiento;
    private String foto;

    public int getEdad() {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }
}

