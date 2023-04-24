package Doggie.WebPage.Mundial.servicio.cache;

import Doggie.WebPage.Mundial.excepciones.EquiposNoInicializadosException;
import Doggie.WebPage.Mundial.modelo.DetallesJugador;
import Doggie.WebPage.Mundial.modelo.DetallesPartido;
import Doggie.WebPage.Mundial.modelo.entidad.Equipo;
import Doggie.WebPage.Mundial.modelo.entidad.Gol;
import Doggie.WebPage.Mundial.modelo.entidad.Participante;
import Doggie.WebPage.Mundial.modelo.entidad.Partido;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@SuppressWarnings("SpellCheckingInspection")
@Slf4j
@Component
public class CargaPartido {

    @SuppressWarnings("SpellCheckingInspection")
    private final JugadorCache jugadorCache;
    private final Map<DetallesPartido, Consumer<Partido>> detallesMap;


    @Autowired
    public CargaPartido(JugadorCache jugadorCache) {
        this.detallesMap = Map.of(
                DetallesPartido.GOLES, this::cargarGoles,
                DetallesPartido.EQUIPOS, this::cargarEquipos,
                DetallesPartido.CONVOCADOS, this::cargarConvocados,
                DetallesPartido.TARJETAS, this::cargarTarjetas,
                DetallesPartido.FASE, this::cargarFase,
                DetallesPartido.ESTADISTICAS, this::cargarEstadisticas
        );
        this.jugadorCache = jugadorCache;
    }

    /**
     * Carga los detalles especificados de una lista de partidos.
     *
     * @param partidos         la lista de partidos a cargar
     * @param detallesBuscados el conjunto de detalles a cargar
     */

    @SuppressWarnings("SpellCheckingInspection")
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public void cargar(List<Partido> partidos, List<DetallesPartido> detallesBuscados, List<DetallesJugador> detallesJugadores) {
        partidos.forEach(partido -> cargar(partido, detallesBuscados, detallesJugadores));
    }

    /**
     * Carga los detalles de un partido según los elementos especificados en el conjunto de detalles buscados.
     *
     * @param partido          el partido del que se quieren cargar los detalles
     * @param detallesBuscados el conjunto de detalles buscados que se quieren cargar en el partido
     * @throws IllegalArgumentException si el conjunto de detalles buscados es nulo o está vacío
     */

    @SuppressWarnings("SpellCheckingInspection")
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public void cargar(Partido partido, List<DetallesPartido> detallesBuscados, List<DetallesJugador> detallesJugadores) {
        if (detallesBuscados == null || detallesBuscados.isEmpty()) {
            throw new IllegalArgumentException("La lista de detalles buscados no puede ser null o estar vacía");
        }

        detallesBuscados.forEach(detalle -> {
            var cargador = detallesMap.get(detalle);
            cargador.accept(partido);
        });

        if (detallesBuscados.contains(DetallesPartido.JUGADORES)) {
            cargarJugadores(partido, detallesJugadores);
        }
    }

    /**
     * Carga los equipos de un partido y los inicializa si aún no han sido cargados.
     *
     * @param partido El partido cuyos equipos se quieren cargar.
     */


    @SuppressWarnings("SpellCheckingInspection")
    private void cargarEquipos(Partido partido) {

        if (noSeHanCargadoLosEquipos(partido)) {
            log.info("Cargando los equipos del partido");
            Hibernate.initialize(partido.getEquiposParticipantes());
        }

        log.debug("Equipos del partido: {}", partido.getEquiposParticipantes()
                .stream().map(Participante::getNombre).toList());
    }

    /**
     * Carga los goles de un partido y los inicializa si aún no han sido cargados.
     *
     * @param partido El partido cuyos goles se quieren cargar.
     */

    @SuppressWarnings("SpellCheckingInspection")
    private void cargarGoles(Partido partido) {

        verificarEquiposInicializados(partido);

        if (!Hibernate.isInitialized(partido.getGoles())) {
            log.info("Cargando los goles del partido: {} VS {}",
                    partido.getEquiposParticipantes().get(0).getNombre(),
                    partido.getEquiposParticipantes().get(1).getNombre());
            Hibernate.initialize(partido.getGoles());
        }
        log.debug("Goles del partido: {}", partido.getGoles().stream().map(Gol::getMinuto).toList());
    }

    /**
     * Carga los jugadores de un partido y los inicializa si aún no han sido cargados.
     *
     * @param partido El partido en el que se desean cargar los jugadores.
     * @throws EquiposNoInicializadosException si la lista de equipos participantes del partido no ha sido inicializada.
     */

    @SuppressWarnings("SpellCheckingInspection")
    private void cargarEstadisticas(Partido partido) {

        verificarEquiposInicializados(partido);

        partido.getEquiposParticipantes().forEach(equipo -> {
            log.info("Cargando las estadísticas del equipo: {}", equipo.getNombre());
            cargarEstadisticas(equipo);
            log.debug("Estadísticas: {}", equipo.getEstadisticas());
        });
    }

    private void cargarConvocados(Partido partido) {

        partido.getConvocados().forEach(convocado -> {
            if (!Hibernate.isInitialized(partido.getConvocados())) {
                Hibernate.initialize(partido.getConvocados());
            }
        });
    }

    private void cargarTarjetas(Partido partido) {
        partido.getTarjetas().forEach(convocado -> {
            if (!Hibernate.isInitialized(partido.getTarjetas())) {
                Hibernate.initialize(partido.getTarjetas());
            }
        });
    }

    /**
     * Carga los jugadores de un partido y los inicializa si aún no han sido cargados.
     *
     * @param partido El partido en el que se desean cargar los jugadores.
     * @throws EquiposNoInicializadosException si la lista de equipos participantes del partido no ha sido inicializada.
     */


    @SuppressWarnings("SpellCheckingInspection")
    private void cargarJugadores(Partido partido, List<DetallesJugador> detallesJugadores) {

        verificarEquiposInicializados(partido);

        if (!Hibernate.isInitialized(partido.getConvocados())) {
            partido.getEquiposParticipantes().stream().map(Participante::getEquipo)
                    .forEach(equipo -> {
                        log.debug("Cargando los jugadores del equipo: {}", equipo.getNombre());
                        cargarJugadores(partido, equipo, detallesJugadores);
                    });
        }
    }

    private void cargarFase(Partido partido) {

        if (!Hibernate.isInitialized(partido.getFase())) {
            log.info("Cargando la fase del partido");
            Hibernate.initialize(partido.getFase());
        }
    }

    /**
     * Carga los estadísticas de un equipo para un partido específico y los inicializa si aún no han sido cargados.
     *
     * @param equipo El equipo del cual se desean cargar sus estadísticas.
     */

    @SuppressWarnings("SpellCheckingInspection")
    private void cargarEstadisticas(Participante equipo) {

        if (!Hibernate.isInitialized(equipo.getEstadisticas())) {
            Hibernate.initialize(equipo.getEstadisticas());
        }
    }

    @SuppressWarnings("SpellCheckingInspection")
    private void cargarJugadores(Partido partido, Equipo equipo, List<DetallesJugador> detallesJugadores) {

        if (!Hibernate.isInitialized(equipo.getJugadores())) {
            var jugadores = jugadorCache.getJugadores(equipo.getEquipoId(),
                    partido.getPartidoId(), detallesJugadores);
            equipo.setJugadores(jugadores);
        }
    }

    @SuppressWarnings("SpellCheckingInspection")
    private void verificarEquiposInicializados(Partido partido) {
        if (noSeHanCargadoLosEquipos(partido)) {
            throw new EquiposNoInicializadosException(partido.getPartidoId());
        }
    }

    /**
     * Verifica si los equipos participantes del partido han sido cargados previamente.
     *
     * @param partido El partido a evaluar.
     * @return true si los equipos no han sido cargados, false de lo contrario.
     */

    @SuppressWarnings("SpellCheckingInspection")
    private boolean noSeHanCargadoLosEquipos(Partido partido) {
        return !Hibernate.isInitialized(partido.getEquiposParticipantes());
    }
}
