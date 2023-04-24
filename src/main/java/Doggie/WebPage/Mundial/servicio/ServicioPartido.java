package Doggie.WebPage.Mundial.servicio;

import Doggie.WebPage.Mundial.dto.*;
import Doggie.WebPage.Mundial.dto.mapper.*;
import Doggie.WebPage.Mundial.modelo.DetallesJugador;
import Doggie.WebPage.Mundial.modelo.DetallesPartido;
import Doggie.WebPage.Mundial.modelo.entidad.*;
import Doggie.WebPage.Mundial.servicio.cache.PartidoCache;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

import java.util.function.Function;

@Service
@RequiredArgsConstructor

public class ServicioPartido {

    private final PlantillaMapper plantillaMapper;
    private final GolesEquipoMapper equipoGolesMapper;
    private final MarcadorMapper marcadorMapper;
    private final DatosPartidoMapper datosPartidoMapper;
    private final DatosEstadisticaMapper datosEstadisticaMapper;
    private final PenalesEquipoMapper penalesEquipoMapper;

    private final PartidoCache partidoCache;

    /**
     * Obtiene toda la información posible de un partido. Esta información es:
     * plantillas de los equipos, goles, penales, marcadores, etc.
     *
     * @param partidoId el ID del partido del que se desea obtener su información.
     * @return un objeto DatosPartido que representa toda la información del partido.
     */

    public DatosPartido datosPartido(Long partidoId) {

        var partido = findPartido(partidoId,
                List.of(DetallesPartido.EQUIPOS, DetallesPartido.JUGADORES, DetallesPartido.GOLES, DetallesPartido.ESTADISTICAS, DetallesPartido.CONVOCADOS, DetallesPartido.TARJETAS),
                List.of(DetallesJugador.CONVOCACIONES, DetallesJugador.TARJETAS, DetallesJugador.GOLES, DetallesJugador.SUSTITUCIONES));

        var plantillas = findPlantillasByPartido(partido);
        var goles = findGolesByPartido(partido);
        var penales = findPenalesByPartido(partido);
        var marcador = findMarcadorByPartido(partido);
        var marcadorPenales = findMarcadorPenalesByPartido(partido);
        var datosEstadisticas = findDatosEstadisticasByPartido(partido);

        if (goles.stream().allMatch(gol -> gol.goles().isEmpty())) {
            goles = null;
        }

        return datosPartidoMapper.from(plantillas, marcador, goles, partido, datosEstadisticas, marcadorPenales, penales);
    }

    /**
     * Obtiene las plantillas de los equipos que participaron en un partido específico.
     *
     * @param partidoId el ID del partido del que se desean obtener las plantillas.
     * @return una lista de objetos Plantilla que representan las plantillas de los equipos que participaron en el partido.
     */

    public List<Plantilla> findPlantillasByPartido(Long partidoId) {
        return findByPartido(partidoId, this::findPlantillasByPartido, List.of(DetallesPartido.EQUIPOS,
                        DetallesPartido.JUGADORES, DetallesPartido.CONVOCADOS),
                List.of(DetallesJugador.CONVOCACIONES, DetallesJugador.TARJETAS, DetallesJugador.SUSTITUCIONES));
    }

    /**
     * Obtiene los goles marcados por cada equipo en un partido específico.
     *
     * @param partidoId el ID del partido del que se quieren obtener los goles.
     * @return una lista de objetos GolesEquipo que contienen los goles marcados por cada equipo en el partido.
     */

    public List<GolesEquipo> findGolesByPartido(Long partidoId) {
        return findByPartido(partidoId, this::findGolesByPartido,
                List.of(DetallesPartido.EQUIPOS, DetallesPartido.JUGADORES, DetallesPartido.CONVOCADOS),
                List.of(DetallesJugador.GOLES));
    }

    /**
     * Obtiene la información de los penales de un partido.
     *
     * @param partidoId el ID del partido del que se desea obtener la información de los penales.
     * @return una lista de objetos Penales que representan la información de los penales del partido especificado.
     */

    public List<PenalesEquipo> findPenalesByPartido(Long partidoId) {
        return findByPartido(partidoId, this::findPenalesByPartido,
                List.of(DetallesPartido.EQUIPOS, DetallesPartido.JUGADORES, DetallesPartido.CONVOCADOS),
                List.of(DetallesJugador.GOLES));
    }

    /**
     * Obtiene el marcador del partido para cada equipo.
     *
     * @param partidoId el identificador del partido del que se desean obtener los goles.
     * @return una lista de objetos MarcadorEquipo que representan el marcador del partido para cada equipo.
     */

    public List<MarcadorEquipo> findMarcadorByPartido(Long partidoId) {
        return findByPartido(partidoId, this::findMarcadorByPartido,
                List.of(DetallesPartido.EQUIPOS, DetallesPartido.GOLES),
                List.of());
    }

    /**
     * Obtiene el marcador de los penales del partido especificado.
     *
     * @param partidoId el ID del partido del que se desea obtener el marcador de los penales.
     * @return una lista de objetos MarcadorEquipo que representan el marcador de los penales del partido.
     */

    public List<MarcadorEquipo> findMarcadorPenalesByPartido(Long partidoId) {
        return findByPartido(partidoId, this::findMarcadorPenalesByPartido,
                List.of(DetallesPartido.EQUIPOS, DetallesPartido.GOLES),
                List.of());
    }

    /**
     * Obtiene las estadísticas de un partido.
     *
     * @param partidoId el ID del partido del que se desean obtener las estadísticas.
     * @return una lista de objetos DatosEstadistica que representan las estadísticas de los equipos que jugaron el partido.
     */

    public List<DatosEstadistica> findDatosEstadisticasByPartido(Long partidoId) {
        return findByPartido(partidoId, this::findDatosEstadisticasByPartido,
                List.of(DetallesPartido.EQUIPOS, DetallesPartido.ESTADISTICAS, DetallesPartido.TARJETAS),
                List.of());
    }

    /**
     * Obtiene las plantillas de los equipos que participaron en un partido específico.
     *
     * @param partido el partido del que se desean obtener las plantillas.
     * @return una lista de objetos Plantilla que representan las plantillas de los equipos que participaron en el partido.
     */

    private List<Plantilla> findPlantillasByPartido(Partido partido) {
        var equipos = partido.getEquiposParticipantes();
        return plantillaMapper.from(equipos);
    }

    /**
     * Obtiene los goles marcados por cada equipo en un partido específico.
     *
     * @param partido el partido del que se quieren obtener los goles.
     * @return una lista de objetos GolesEquipo que contienen los goles marcados por cada equipo en el partido.
     */

    private List<GolesEquipo> findGolesByPartido(Partido partido) {
        var equipos = partido.getEquiposParticipantes();
        return equipoGolesMapper.from(equipos);
    }

    /**
     * Obtiene la información de los penales de un partido.
     *
     * @param partido el partido del que se desea obtener la información de los penales.
     * @return una lista de objetos Penales que representan la información de los penales del partido especificado.
     */

    private List<PenalesEquipo> findPenalesByPartido(Partido partido) {
        var equipos = partido.getEquiposParticipantes();
        return penalesEquipoMapper.from(equipos);
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
     * Obtiene el marcador de los penales del partido especificado.
     *
     * @param partido el partido del que se desea obtener el marcador de los penales.
     * @return una lista de objetos MarcadorEquipo que representan el marcador de los penales del partido.
     */

    private List<MarcadorEquipo> findMarcadorPenalesByPartido(Partido partido) {
        return marcadorMapper.marcadorPenalesFrom(partido);
    }

    /**
     * Obtiene las estadísticas de un partido.
     *
     * @param partido el partido del que se desean obtener las estadísticas.
     * @return una lista de objetos DatosEstadistica que representan las estadísticas de los equipos que jugaron el partido.
     */

    public List<DatosEstadistica> findDatosEstadisticasByPartido(Partido partido) {
        var equipos = partido.getEquiposParticipantes();
        return datosEstadisticaMapper.from(equipos);
    }

    /**
     * Busca una lista de elementos por partido.
     *
     * @param partidoId        el ID del partido por el cual se quieren buscar los elementos.
     * @param findByPartido    la función que realiza la búsqueda de los elementos por partido.
     * @param detallesBusqueda los detalles adicionales de búsqueda del partido.
     * @return una lista de elementos que pertenecen al partido especificado.
     */

    private <T> List<T> findByPartido(Long partidoId, Function<Partido, List<T>> findByPartido, List<DetallesPartido> detallesBusqueda, List<DetallesJugador> detallesJugadores) {
        var partido = findPartido(partidoId, detallesBusqueda, detallesJugadores);
        return findByPartido.apply(partido);
    }

    /**
     * Obtiene un partido cargado desde la caché con sus recursos o lo busca en la base de datos.
     *
     * @param partidoId               el ID del partido del que se desea obtener.
     * @param detallesBusquedaPartido una lista con el nombre de los recursos que se quieren cargar.
     * @return un partido con sus recursos cargados.
     */

    @Transactional
    private Partido findPartido(Long partidoId, List<DetallesPartido> detallesBusquedaPartido, List<DetallesJugador> detallesBusquedaJugador) {
        return partidoCache.getPartido(partidoId, detallesBusquedaPartido, detallesBusquedaJugador);
    }
}
