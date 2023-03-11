package Doggie.WebPage.Mundial.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "equipo")
@Getter
@Setter
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long equipoId;

    @Column(unique = true)
    private String nombre;

    @Column(unique = true)
    private String codigoFIFA;

    @Column(unique = true)
    private String bandera;

    @ManyToOne
    @JsonManagedReference
    private Grupo grupo;

    @OneToMany(mappedBy = "equipo", fetch = FetchType.EAGER)
    private List<Jugador> jugadores;

    @JsonBackReference
    @OneToMany(mappedBy = "equipo", fetch = FetchType.EAGER)
    private List<EquipoEnPartido> juegosJugados;

    @OneToOne
    private DirectorTecnico directorTecnico;
}
