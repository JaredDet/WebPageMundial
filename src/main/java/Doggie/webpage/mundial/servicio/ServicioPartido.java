package Doggie.WebPage.Mundial.servicio;

import Doggie.WebPage.Mundial.dto.*;
import Doggie.WebPage.Mundial.dto.mapper.*;
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
    private final DatosExtrasPartidoMapper datosExtrasPartidoMapper;
    private final PartidoCache partidoCache;

    /**
     * Obtiene toda la información posible de un partido. Esta información es:
     * plantillas de los equipos, goles, penales, marcadores, etc.
     *
     * @param partidoId el ID del partido del que se desea obtener su información.
     * @return un objeto DatosPartido que representa toda la información del partido.
     */

    public DatosPartido datosPartido(Long partidoId) {

        var partido = findPartido(partidoId);

        var plantillas = findPlantillasByPartido(partido);
        var goles = findGolesByPartido(partido);
        var penales = findPenalesByPartido(partido);
        var marcador = findMarcadorByPartido(partido);
        var marcadorPenales = findMarcadorPenalesByPartido(partido);
        var datosEstadisticas = findDatosEstadisticasByPartido(partido);
        var datosExtras = findDatosExtrasByPartido(partido);

        if (goles.stream().allMatch(gol -> gol.goles().isEmpty())) {
            goles = null;
        }

        return datosPartidoMapper.from(plantillas, marcador, goles, datosExtras, datosEstadisticas, marcadorPenales, penales, partido);
    }

    /**
     * Obtiene las plantillas de los equipos que participaron en un partido específico.
     *
     * @param partidoId el ID del partido del que se desean obtener las plantillas.
     * @return una lista de objetos Plantilla que representan las plantillas de los equipos que participaron en el partido.
     */

    public List<Plantilla> findPlantillasByPartido(Long partidoId) {
        return findByPartido(partidoId, this::findPlantillasByPartido);
    }

    /**
     * Obtiene los goles marcados por cada equipo en un partido específico.
     *
     * @param partidoId el ID del partido del que se quieren obtener los goles.
     * @return una lista de objetos GolesEquipo que contienen los goles marcados por cada equipo en el partido.
     */

    public List<GolesEquipo> findGolesByPartido(Long partidoId) {
        return findByPartido(partidoId, this::findGolesByPartido);
    }

    /**
     * Obtiene la información de los penales de un partido.
     *
     * @param partidoId el ID del partido del que se desea obtener la información de los penales.
     * @return una lista de objetos Penales que representan la información de los penales del partido especificado.
     */

    public List<PenalesEquipo> findPenalesByPartido(Long partidoId) {
        return findByPartido(partidoId, this::findPenalesByPartido);
    }

    /**
     * Obtiene el marcador del partido para cada equipo.
     *
     * @param partidoId el identificador del partido del que se desean obtener los goles.
     * @return una lista de objetos MarcadorEquipo que representan el marcador del partido para cada equipo.
     */

    public List<MarcadorEquipo> findMarcadorByPartido(Long partidoId) {
        return findByPartido(partidoId, this::findMarcadorByPartido);
    }

    /**
     * Obtiene el marcador de los penales del partido especificado.
     *
     * @param partidoId el ID del partido del que se desea obtener el marcador de los penales.
     * @return una lista de objetos MarcadorEquipo que representan el marcador de los penales del partido.
     */

    public List<MarcadorEquipo> findMarcadorPenalesByPartido(Long partidoId) {
        return findByPartido(partidoId, this::findMarcadorPenalesByPartido);
    }

    /**
     * Obtiene las estadísticas de un partido.
     *
     * @param partidoId el ID del partido del que se desean obtener las estadísticas.
     * @return una lista de objetos DatosEstadistica que representan las estadísticas de los equipos que jugaron el partido.
     */

    public List<DatosEstadistica> findDatosEstadisticasByPartido(Long partidoId) {
        return findByPartido(partidoId, this::findDatosEstadisticasByPartido);
    }

    public DatosExtrasPartido findDatosExtrasByPartido(Long partidoId) {
        return findByPartidoSimple(partidoId, this::findDatosExtrasByPartido);
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

    private DatosExtrasPartido findDatosExtrasByPartido(Partido partido) {
        return datosExtrasPartidoMapper.from(partido);
    }

    /**
     * Busca una lista de elementos por partido.
     *
     * @param partidoId        el ID del partido por el cual se quieren buscar los elementos.
     * @param findByPartido    la función que realiza la búsqueda de los elementos por partido.
     * @return una lista de elementos que pertenecen al partido especificado.
     */

    private <T> List<T> findByPartido(Long partidoId, Function<Partido, List<T>> findByPartido) {
        var partido = findPartido(partidoId);
        return findByPartido.apply(partido);
    }

    private <T> T findByPartidoSimple(Long partidoId, Function<Partido, T> findByPartido) {
        var partido = findPartido(partidoId);
        return findByPartido.apply(partido);
    }

    /**
     * Obtiene un partido cargado desde la caché con sus recursos o lo busca en la base de datos.
     *
     * @param partidoId               el ID del partido del que se desea obtener.
     * @return un partido con sus recursos cargados.
     */

    @Transactional
    private Partido findPartido(Long partidoId) {
        return partidoCache.getPartido(partidoId);
    }
}
