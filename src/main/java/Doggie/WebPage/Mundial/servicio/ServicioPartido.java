package Doggie.WebPage.Mundial.servicio;

import Doggie.WebPage.Mundial.dto.*;
import Doggie.WebPage.Mundial.dto.mapper.*;
import Doggie.WebPage.Mundial.excepciones.EquipoLocalNoEncontradoException;
import Doggie.WebPage.Mundial.modelo.DetallesPartido;
import Doggie.WebPage.Mundial.modelo.entidad.*;
import Doggie.WebPage.Mundial.servicio.cache.PartidoCache;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;

import java.util.function.BiFunction;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j

public class ServicioPartido {

    private final ServicioJugador servicioJugador;

    private final PlantillaMapper plantillaMapper;
    private final GolesEquipoMapper equipoGolesMapper;
    private final MarcadorMapper marcadorMapper;
    private final DatosPartidoMapper datosPartidoMapper;
    private final DatosEstadisticaMapper datosEstadisticaMapper;
    private final PenalesMapper penalesMapper;

    private final PartidoCache partidoCache;

    /**
     * Obtiene toda la información posible de un partido. Esta información es:
     * plantillas de los equipos, goles, penales, marcadores, etc.
     *
     * @param partidoId el ID del partido del que se desea obtener su información.
     * @return un objeto DatosPartido que representa toda la información del partido.
     */

    public DatosPartido datosPartido(Long partidoId) {

        var partido = findPartido(partidoId, List.of(DetallesPartido.EQUIPOS, DetallesPartido.JUGADORES, DetallesPartido.GOLES, DetallesPartido.ESTADISTICAS));

        var plantillas = findPlantillasByPartido(partido);
        var golesEquipos = findGolesByPartido(partido);
        var datosEstadisticas = findDatosEstadisticasByPartido(partido);
        var penales = findPenalesByPartido(partido);
        var marcador = findMarcadorByPartido(partido);
        var marcadorPenales = findMarcadorPenalesByPartido(partido);

        if (golesEquipos.stream().allMatch(gol -> gol.goles().isEmpty())) {
            golesEquipos = null;
        }

        return datosPartidoMapper.from(plantillas, marcador, golesEquipos, partido, datosEstadisticas, marcadorPenales, penales);
    }

    /**
     * Obtiene las plantillas de los equipos que participaron en un partido específico.
     *
     * @param partidoId el ID del partido del que se desean obtener las plantillas.
     * @return una lista de objetos Plantilla que representan las plantillas de los equipos que participaron en el partido.
     */

    public List<Plantilla> findPlantillasByPartido(Long partidoId) {
        return findByPartido(partidoId, this::findPlantillasByPartido, List.of(DetallesPartido.EQUIPOS, DetallesPartido.JUGADORES));
    }

    /**
     * Obtiene los goles marcados por cada equipo en un partido específico.
     *
     * @param partidoId el ID del partido del que se quieren obtener los goles.
     * @return una lista de objetos GolesEquipo que contienen los goles marcados por cada equipo en el partido.
     */

    public List<GolesEquipo> findGolesByPartido(Long partidoId) {
        return findByPartido(partidoId, this::findGolesByPartido, List.of(DetallesPartido.EQUIPOS, DetallesPartido.JUGADORES));
    }

    /**
     * Obtiene el marcador del partido para cada equipo.
     *
     * @param partidoId el identificador del partido del que se desean obtener los goles.
     * @return una lista de objetos MarcadorEquipo que representan el marcador del partido para cada equipo.
     */

    public List<MarcadorEquipo> findMarcadorByPartido(Long partidoId) {
        return findByPartido(partidoId, this::findMarcadorByPartido, List.of(DetallesPartido.EQUIPOS, DetallesPartido.GOLES));
    }

    /**
     * Obtiene las estadísticas de un partido.
     *
     * @param partidoId el ID del partido del que se desean obtener las estadísticas.
     * @return una lista de objetos DatosEstadistica que representan las estadísticas de los equipos que jugaron el partido.
     */

    public List<DatosEstadistica> findDatosEstadisticasByPartido(Long partidoId) {
        return findByPartido(partidoId, partido -> {
            var datosLocal = findDatosEstadisticasByPartido(partido, true);
            var datosVisita = findDatosEstadisticasByPartido(partido, false);
            return List.of(datosLocal, datosVisita);
        }, List.of(DetallesPartido.EQUIPOS, DetallesPartido.JUGADORES, DetallesPartido.ESTADISTICAS));
    }

    /**
     * Obtiene la información de los penales de un partido.
     *
     * @param partidoId el ID del partido del que se desea obtener la información de los penales.
     * @return una lista de objetos Penales que representan la información de los penales del partido especificado.
     */

    public List<Penales> findPenalesByPartido(Long partidoId) {
        return findByPartido(partidoId, this::findPenalesByPartido, List.of(DetallesPartido.EQUIPOS, DetallesPartido.JUGADORES));
    }

    /**
     * Obtiene el marcador de los penales del partido especificado.
     *
     * @param partidoId el ID del partido del que se desea obtener el marcador de los penales.
     * @return una lista de objetos MarcadorEquipo que representan el marcador de los penales del partido.
     */

    public List<MarcadorEquipo> findMarcadorPenalesByPartido(Long partidoId) {
        return findByPartido(partidoId, this::findMarcadorPenalesByPartido, List.of(DetallesPartido.EQUIPOS, DetallesPartido.GOLES));
    }

    /**
     * Obtiene las plantillas de los equipos que participaron en un partido específico.
     *
     * @param partido el partido del que se desean obtener las plantillas.
     * @return una lista de objetos Plantilla que representan las plantillas de los equipos que participaron en el partido.
     */

    private List<Plantilla> findPlantillasByPartido(Partido partido) {
        return findDataEquiposByPartido(partido, this::findPlantillaByPartido);
    }

    /**
     * Obtiene los goles marcados por cada equipo en un partido específico.
     *
     * @param partido el partido del que se quieren obtener los goles.
     * @return una lista de objetos GolesEquipo que contienen los goles marcados por cada equipo en el partido.
     */

    private List<GolesEquipo> findGolesByPartido(Partido partido) {
        return findDataEquiposByPartido(partido, this::findGolesEquipoByPartido);
    }

    /**
     * Obtiene el marcador del partido para cada equipo.
     *
     * @param partido el partido del que se desean obtener los goles.
     * @return una lista de objetos MarcadorEquipo que representan el marcador del partido para cada equipo.
     */

    private List<MarcadorEquipo> findMarcadorByPartido(Partido partido) {
        return marcadorMapper.marcadorGolesFrom(partido);
    }

    /**
     * Obtiene las estadísticas de un partido.
     *
     * @param partido el partido del que se desean obtener las estadísticas.
     * @return una lista de objetos DatosEstadistica que representan las estadísticas de los equipos que jugaron el partido.
     */

    public List<DatosEstadistica> findDatosEstadisticasByPartido(Partido partido) {
        return findDataEquiposByPartido(partido, this::findDatosEstadisticasByPartido);
    }

    /**
     * Obtiene la información de los penales de un partido.
     *
     * @param partido el partido del que se desea obtener la información de los penales.
     * @return una lista de objetos Penales que representan la información de los penales del partido especificado.
     */

    private List<Penales> findPenalesByPartido(Partido partido) {
        return findDataEquiposByPartido(partido, this::findPenalesEquipoByPartido);
    }

    /**
     * Obtiene la plantilla de uno de los equipos que participaron en un partido específico.
     *
     * @param esLocal condición booleana que representa si el equipo es el equipo local o no.
     * @param partido el partido del que se desea obtener la plantilla.
     * @return un objeto Plantilla que representa la plantilla de uno de los equipos que participaron en el partido.
     */

    private Plantilla findPlantillaByPartido(Partido partido, boolean esLocal) {
        return findDataByPartido(partido, esLocal, servicioJugador::findByEquipoYPartido, plantillaMapper::from);
    }

    private DatosEstadistica findDatosEstadisticasByPartido(Partido partido, boolean esLocal) {
        return findDataByPartido(partido, esLocal, servicioJugador::findByEquipoYPartido,
                estadisticaMapper -> datosEstadisticaMapper.from(estadisticaMapper.getPartidosParticipados().get(0))
        );
    }

    private GolesEquipo findGolesEquipoByPartido(Partido partido, boolean esLocal) {
        return findDataEquipoByPartido(partido, esLocal, equipo -> servicioJugador.findJugadorConGolesByEquipoYPartido(equipo, partido, false), equipoGolesMapper::from);
    }

    private Penales findPenalesEquipoByPartido(Partido partido, boolean esLocal) {
        return findDataEquipoByPartido(partido, esLocal, equipo -> servicioJugador.findJugadorConGolesByEquipoYPartido(equipo, partido, true), penalesMapper::from);
    }

    private List<MarcadorEquipo> findMarcadorPenalesByPartido(Partido partido) {
        return marcadorMapper.marcadorPenalesFrom(partido);
    }

    private <T> List<T> findByPartido(Long partidoId, Function<Partido, List<T>> findByPartido, List<DetallesPartido> detallesBusqueda) {
        var partido = findPartido(partidoId, detallesBusqueda);
        return findByPartido.apply(partido);
    }

    private <T> T findDataByPartido(Partido partido, boolean esLocal, BiFunction<Equipo, Partido, List<Jugador>> jugadorFinder, Function<Equipo, T> dataMapper) {
        var equipo = filterEsLocal(partido.getEquiposParticipantes(), esLocal);
        filterPartidosParticipadosByPartido(equipo, partido);
        var jugadores = jugadorFinder.apply(equipo, partido);
        equipo.setJugadores(jugadores);
        return dataMapper.apply(equipo);
    }

    private <T> List<T> findDataEquiposByPartido(Partido partido, BiFunction<Partido, Boolean, T> equipoFinder) {
        var equipoLocal = equipoFinder.apply(partido, true);
        var equipoVisita = equipoFinder.apply(partido, false);
        return List.of(equipoLocal, equipoVisita);
    }

    private <T> T findDataEquipoByPartido(Partido partido, boolean esLocal, Function<Equipo, List<Jugador>> findJugadoresFunction, Function<Equipo, T> mapperFunction) {
        var equipo = filterEsLocal(partido.getEquiposParticipantes(), esLocal);
        var jugadores = findJugadoresFunction.apply(equipo);
        equipo.setJugadores(jugadores);
        return mapperFunction.apply(equipo);
    }

    private Equipo filterEsLocal(List<Participante> participantes, boolean esLocal) {
        return participantes.stream()
                .filter(participante -> participante.isEsLocal() == esLocal)
                .map(Participante::getEquipo)
                .findFirst().orElseThrow(EquipoLocalNoEncontradoException::new);
    }

    private void filterPartidosParticipadosByPartido(Equipo equipo, Partido partido) {
        equipo.getPartidosParticipados().removeIf(participacion -> !participacion.getPartido().equals(partido));
    }

    @Transactional
    private Partido findPartido(Long partidoId, List<DetallesPartido> detallesBusqueda) {
        return partidoCache.getPartido(partidoId, detallesBusqueda);
    }
}
