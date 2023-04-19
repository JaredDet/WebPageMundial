package Doggie.WebPage.Mundial.servicio.cache;

import Doggie.WebPage.Mundial.modelo.DetallesJugador;
import Doggie.WebPage.Mundial.modelo.entidad.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;


@Slf4j
@Component
public class CargaJugador {
    private final Map<DetallesJugador, Consumer<Jugador>> detallesMap;

    public CargaJugador() {
        this.detallesMap = Map.of(
                DetallesJugador.CONVOCACIONES, this::cargarConvocaciones,
                DetallesJugador.TARJETAS, this::cargarTarjetas,
                DetallesJugador.SUSTITUCIONES, this::cargarHistorialSustituciones,
                DetallesJugador.GOLES, this::cargarGoles
        );
    }

    /**
     * Carga los detalles especificados de una lista de jugadores.
     *
     * @param jugadores        la lista de jugadores a cargar
     * @param detallesBuscados el conjunto de detalles a cargar
     */

    @Transactional(readOnly = true)
    public void cargar(List<Jugador> jugadores, List<DetallesJugador> detallesBuscados) {
        jugadores.forEach(jugador -> cargar(jugador, detallesBuscados));
    }

    /**
     * Carga los detalles de un partido según los elementos especificados en el conjunto de detalles buscados.
     *
     * @param jugador          el jugador del que se quieren cargar los detalles
     * @param detallesBuscados el conjunto de detalles buscados que se quieren cargar en el jugador
     * @throws IllegalArgumentException si el conjunto de detalles buscados es nulo o está vacío
     */

    @Transactional(readOnly = true)
    public void cargar(Jugador jugador, List<DetallesJugador> detallesBuscados) {
        if (detallesBuscados == null || detallesBuscados.isEmpty()) {
            throw new IllegalArgumentException("La lista de detalles buscados no puede ser null o estar vacía");
        }

        detallesBuscados.forEach(detalle -> {
            var cargador = detallesMap.get(detalle);
            cargador.accept(jugador);
        });
    }

    /**
     * Carga las convocatorias del jugador especificado y las inicializa si aún no han sido cargadas.
     *
     * @param jugador El jugador cuyas convocatorias se deben cargar.
     */

    private void cargarConvocaciones(Jugador jugador) {

        if (Hibernate.isInitialized(jugador.getConvocaciones())) {
            Hibernate.initialize(jugador.getConvocaciones());
        }
    }

    /**
     * Carga y inicializa las sustituciones del jugador especificado si aún no han sido cargadas.
     *
     * @param jugador el objeto Jugador cuyo historial de sustituciones se quiere cargar.
     */

    private void cargarHistorialSustituciones(Jugador jugador) {

        if (!Hibernate.isInitialized(jugador.getHistorialSustituciones())) {
            Hibernate.initialize(jugador.getHistorialSustituciones());
            jugador.getHistorialSustituciones().forEach(this::cargarCambios);
        }
    }

    /**
     * Carga la sustitución asociada a un objeto Cambio si aún no ha sido inicializada.
     * Si la sustitución ya ha sido inicializada, no hace nada.
     *
     * @param cambio el objeto Cambio del que se desea cargar la sustitución.
     */

    private void cargarCambios(Cambio cambio) {

        if (!Hibernate.isInitialized(cambio.getSustitucion())) {
            Hibernate.initialize(cambio.getSustitucion());
        }
    }

    /**
     * Carga las tarjetas del jugador especificado y las inicializa si aún no han sido cargadas.
     *
     * @param jugador El jugador cuyas tarjetas se deben cargar.
     */

    private void cargarTarjetas(Jugador jugador) {

        if (!Hibernate.isInitialized(jugador.getTarjetas())) {
            Hibernate.initialize(jugador.getTarjetas());
        }
    }

    /**
     * Carga los goles del jugador especificado y las inicializa si aún no han sido cargadas.
     *
     * @param jugador El jugador cuyos goles se deben cargar.
     */

    private void cargarGoles(Jugador jugador) {

        if (!Hibernate.isInitialized(jugador.getGoles())) {
            log.info("Cargando los goles del jugador: {}", jugador.getNombre());
            Hibernate.initialize(jugador.getGoles());
        }
        log.debug("Goles: {}", jugador.getGoles().stream()
                .map(Gol::getMinuto).toList());
    }
}
