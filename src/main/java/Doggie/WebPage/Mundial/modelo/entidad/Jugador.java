package Doggie.WebPage.Mundial.modelo.entidad;

import Doggie.WebPage.Mundial.excepciones.JugadorNoEncontradoException;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "jugadores")
@Getter
@Setter
@ToString(exclude = {"goles", "tarjetas", "convocaciones", "historialSustituciones"})
@EqualsAndHashCode

public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jugadorId")
    private Long jugadorId;
    private String nombre;
    private int dorsal;

    @JsonBackReference
    @ManyToOne
    private Equipo equipo;

    @JsonManagedReference
    @OneToMany(mappedBy = "jugador", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Gol> goles;

    @JsonManagedReference
    @OneToMany(mappedBy = "jugador", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Tarjeta> tarjetas;

    @JsonManagedReference
    @OneToMany(mappedBy = "jugador", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Convocado> convocaciones;

    @JsonManagedReference
    @OneToMany(mappedBy = "jugador", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Cambio> historialSustituciones;

    public boolean anotoGolesReglamentariosEnPartido(Partido partido) {
        return goles.stream()
                .anyMatch(gol -> gol.getPartido().getPartidoId()
                        .equals(partido.getPartidoId()) && gol.esGolReglamentario());
    }

    public boolean anotoPenalesEnTandaPenal(Partido partido) {
        return goles.stream()
                .anyMatch(gol -> gol.getPartido().getPartidoId()
                        .equals(partido.getPartidoId()) && gol.esGolTandaPenales());
    }


    public List<Gol> getGolesPorPartido(Partido partido) {

        return goles.stream()
                .filter(gol -> gol.getPartido().getPartidoId()
                        .equals(partido.getPartidoId()))
                .toList();
    }

    public Convocado getConvocacionPorPartido(Partido partido) {

        return convocaciones.stream()
                .filter(convocacion -> convocacion.getPartido().getPartidoId()
                        .equals(partido.getPartidoId()))
                .findFirst().orElseThrow(() ->
                        new JugadorNoEncontradoException(nombre, partido.getPartidoId())
                );
    }

    public List<Tarjeta> getTarjetasPorPartido(Partido partido) {
        return tarjetas.stream()
                .filter(convocacion -> convocacion.getPartido().getPartidoId()
                        .equals(partido.getPartidoId())).toList();
    }

    public Cambio getSustitucionPorPartido(Partido partido) {

        return historialSustituciones.stream()
                .filter(sustitucion -> sustitucion.getPartido().getPartidoId()
                        .equals(partido.getPartidoId()))
                .findFirst().orElse(null);
    }

    public int getVecesJugadorPartido() {
        return (int) convocaciones
                .stream()
                .map(Convocado::isEsJugadorPartido)
                .count();
    }
}


