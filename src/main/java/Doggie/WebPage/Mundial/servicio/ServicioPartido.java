package Doggie.WebPage.Mundial.servicio;

import Doggie.WebPage.Mundial.dto.*;
import Doggie.WebPage.Mundial.dto.mapper.*;
import Doggie.WebPage.Mundial.modelo.entidad.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@Service
@RequiredArgsConstructor

public class ServicioPartido {

    private final ServicioJugador servicioJugador;

    private final PlantillaMapper plantillaMapper;
    private final GolesEquipoMapper equipoGolesMapper;
    private final MarcadorMapper marcadorMapper;
    private final DatosPartidoMapper datosPartidoMapper;
    private final DatosEstadisticaMapper datosEstadisticaMapper;
    private final PenalesMapper penalesMapper;

    private final PartidoCache partidoCache;

    public DatosPartido datosPartido(Long partidoId) {
        var partido = findPartido(partidoId);

        if (partido == null) {
            return null;
        }

        var plantillas = findPlantillasByPartido(partido);
        var golesEquipos = findGolesByPartido(partido);
        var marcador = findMarcadorByPartido(partido);
        var datosEstadisticas = findDatosEstadisticasByPartido(partido);
        var penales = findPenalesByPartido(partido);
        var marcadorPenales = findMarcadorPenalesByPartido(partido);

        if (golesEquipos.stream().allMatch(gol -> gol.goles().isEmpty())) {
            golesEquipos = null;
        }

        return datosPartidoMapper.from(plantillas, marcador, golesEquipos, partido, datosEstadisticas, marcadorPenales, penales);
    }

    public List<Plantilla> findPlantillasByPartido(Long partidoId) {
        return findByPartido(partidoId, this::findPlantillasByPartido);
    }

    public List<GolesEquipo> findGolesByPartido(Long partidoId) {
        return findByPartido(partidoId, this::findGolesByPartido);
    }

    public List<MarcadorEquipo> findMarcadorByPartido(Long partidoId) {
        return findByPartido(partidoId, this::findMarcadorByPartido);
    }

    public List<DatosEstadistica> findDatosEstadisticasByPartido(Long partidoId) {
        return findByPartido(partidoId, partido -> {
            var datosLocal = findDatosEstadisticasByPartido(partido, true);
            var datosVisita = findDatosEstadisticasByPartido(partido, false);
            return List.of(datosLocal, datosVisita);
        });
    }

    public List<Penales> findPenalesByPartido(Long partidoId) {
        return findByPartido(partidoId, this::findPenalesByPartido);
    }

    public List<MarcadorEquipo> findMarcadorPenalesByPartido(Long partidoId) {
        return findByPartido(partidoId, this::findMarcadorPenalesByPartido);
    }

    private List<Plantilla> findPlantillasByPartido(Partido partido) {
        return findDataEquiposByPartido(partido, this::findPlantillaByPartido);
    }

    private List<GolesEquipo> findGolesByPartido(Partido partido) {
        return findDataEquiposByPartido(partido, this::findGolesEquipoByPartido);
    }


    private List<MarcadorEquipo> findMarcadorByPartido(Partido partido) {
        return marcadorMapper.marcadorGolesFrom(partido);
    }

    public List<DatosEstadistica> findDatosEstadisticasByPartido(Partido partido) {
        return findDataEquiposByPartido(partido, this::findDatosEstadisticasByPartido);
    }

    private List<Penales> findPenalesByPartido(Partido partido) {
        return findDataEquiposByPartido(partido, this::findPenalesEquipoByPartido);
    }

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

    private <T> List<T> findByPartido(Long partidoId, Function<Partido, List<T>> findByPartido) {
        var partido = findPartido(partidoId);
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
                .findFirst()
                .orElse(null);
    }

    private void filterPartidosParticipadosByPartido(Equipo equipo, Partido partido) {
        equipo.getPartidosParticipados().removeIf(participacion -> !participacion.getPartido().equals(partido));
    }

    private Partido findPartido(Long partidoId) {
        return partidoCache.getPartidoById(partidoId);
    }
}
