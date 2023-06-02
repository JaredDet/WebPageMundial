package Doggie.WebPage.Mundial.modelo.entidad;

import Doggie.WebPage.Mundial.modelo.Color;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Entity
@Table(name = "partidos")
@Getter
@Setter
@ToString(exclude = {"equiposParticipantes", "convocados", "goles", "sustituciones", "tarjetas"})
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partidoId")
    private Long partidoId;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Temporal(TemporalType.TIME)
    private Date hora;

    @Column(columnDefinition = "boolean default false")
    private boolean tandaPenales;

    @JsonManagedReference
    @OneToMany(mappedBy = "partido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Participante> equiposParticipantes;

    @JsonManagedReference
    @OneToMany(mappedBy = "partido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Convocado> convocados;

    @JsonManagedReference
    @OneToMany(mappedBy = "partido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Gol> goles;

    @JsonManagedReference
    @OneToMany(mappedBy = "partido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sustitucion> sustituciones;

    @JsonManagedReference
    @OneToMany(mappedBy = "partido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Tarjeta> tarjetas;

    @JsonBackReference
    @OneToOne
    private Estadio estadio;

    @JsonBackReference
    @OneToOne
    private Arbitro arbitro;

    @JsonBackReference
    @ManyToOne
    private Fase fase;

    public int tarjetasEquipo(Equipo equipo, Color color) {
        var tarjetasEquipo = tarjetasEquipo(equipo);
        return (int) tarjetasEquipo
                .stream()
                .filter(tarjeta -> tarjeta.getColor()
                        .equals(color))
                .count();
    }

    private List<Tarjeta> tarjetasEquipo(Equipo equipo) {
        return tarjetas.stream()
                .filter(tarjeta -> tarjeta.getJugador()
                        .getEquipo()
                        .getEquipoId()
                        .equals(equipo.getEquipoId()))
                .toList();
    }

    /**
     * Devuelve la cantidad total de goles anotados por un equipo en el torneo,
     * restando los penales que fueron o no goles. Un penal se considera gol si
     * * su propiedad 'entro' es verdadera y errado si su propiedad 'entro' es falsa.
     *
     * @param equipo el equipo del que se quiere obtener la cantidad de goles.
     * @return la cantidad total de goles anotados por el equipo, contabilizando solo los goles válidos.
     */

    public int golesEquipo(Equipo equipo) {
        var cantidadGoles = listaGolesEquipo(equipo).size();
        var penales = penales(equipo, Gol::isEntro) + penales(equipo, Gol::noEntro);
        return cantidadGoles - penales;
    }

    /**
     * Devuelve el número de penales que ha anotado un equipo en el torneo.
     *
     * @param equipo el equipo del que se quiere conocer el número de penales anotados.
     * @return el número de penales anotados por el equipo.
     */

    public int penales(Equipo equipo) {
        return penales(equipo, Gol::isEntro);
    }

    /**
     * Devuelve una lista de todos los goles marcados por un equipo en el torneo.
     *
     * @param equipo el equipo del que se desea obtener los goles.
     * @return una lista de goles marcados por el equipo.
     */

    public List<Gol> listaGolesEquipo(Equipo equipo) {
        return goles.stream()
                .filter(gol -> gol.getJugador()
                        .getEquipo()
                        .getEquipoId()
                        .equals(equipo.getEquipoId()))
                .toList();
    }

    /**
     * Devuelve la cantidad de goles anotados por el equipo rival de un equipo
     * participante en el torneo. Si no hay un equipo rival (por ejemplo, si el
     * equipo ya ha sido eliminado), devuelve 0.
     *
     * @param equipo el equipo del que se quiere conocer los goles del rival
     * @return la cantidad de goles anotados por el equipo rival
     */
    public int golesRival(Equipo equipo) {
        var rival = getRival(equipo);
        return rival.map(this::golesEquipo).orElse(0);
    }

    /**
     * Retorna la cantidad de goles de penales realizados por el equipo que cumplan con el predicado especificado.
     *
     * @param equipo    el equipo del cual se desea obtener la cantidad de goles de penales.
     * @param predicado el predicado que se utilizará para filtrar los goles de penales.
     * @return la cantidad de goles de penales que cumplan con el predicado especificado.
     */

    private int penales(Equipo equipo, Predicate<Gol> predicado) {
        var listaGoles = listaGolesEquipo(equipo);
        return (int) listaGoles.stream()
                .filter(gol -> gol.getMinuto() == null && gol.isPenal() && predicado.test(gol))
                .count();
    }

    /**
     * Devuelve el equipo rival de un equipo participante en el torneo.
     * Si no hay un equipo rival (por ejemplo, si el equipo ya ha sido eliminado),
     * devuelve un Optional vacío.
     *
     * @param equipo el equipo del que se quiere obtener el rival
     * @return un Optional que contiene el equipo rival, o un Optional vacío si no hay rival
     */

    private Optional<Equipo> getRival(Equipo equipo) {
        return equiposParticipantes.stream()
                .filter(participante -> participante.getEquipo() != equipo)
                .findFirst()
                .map(Participante::getEquipo);
    }

    public List<Jugador> jugadoresEquipo(Equipo equipo) {
        return convocados.stream()
                .map(Convocado::getJugador)
                .filter(jugador -> jugador.getEquipo().getEquipoId()
                        .equals(equipo.getEquipoId()))
                .toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partido partido = (Partido) o;
        return Objects.equals(partidoId, partido.partidoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partidoId);
    }
}
