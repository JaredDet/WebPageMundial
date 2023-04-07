package Doggie.WebPage.Mundial.servicio;

import Doggie.WebPage.Mundial.dto.*;
import Doggie.WebPage.Mundial.dto.mapper.*;
import Doggie.WebPage.Mundial.modelo.entidad.*;
import Doggie.WebPage.Mundial.modelo.repositorio.RepositorioPartido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ServicioPartido {

    private final ServicioJugador servicioJugador;

    private final PlantillaMapper plantillaMapper;
    private final GolesEquipoMapper equipoGolesMapper;
    private final MarcadorMapper marcadorMapper;
    private final RepositorioPartido repositorioPartido;

    private final DatosPartidoMapper datosPartidoMapper;
    private final DatosEstadisticaMapper datosEstadisticaMapper;

    public DatosPartido datosPartido(Long partidoId) {
        var partido = repositorioPartido.findById(partidoId).get();
        var plantillas = findByPartido(partido);
        var golesEquipos = findGolesByPartido(partido);
        var marcador = findMarcadorByPartido(partido);
        var datosEstadisticas = findDatosEstadisticasByPartido(partido);
        return datosPartidoMapper.from(plantillas, marcador, golesEquipos, partido, datosEstadisticas);
    }

    public List<Plantilla> findByPartido(Long partidoId) {
        var partido = repositorioPartido.findById(partidoId).get();
        return findByPartido(partido);
    }

    public List<GolesEquipo> findGolesByPartido(Long partidoId) {
        var partido = repositorioPartido.findById(partidoId).get();
        return findGolesByPartido(partido);
    }

    public Marcador findMarcadorByPartido(Long partidoId) {
        var partido = repositorioPartido.findById(partidoId).get();
        return findMarcadorByPartido(partido);
    }

    public List<DatosEstadistica> findDatosEstadisticasByPartido(Long partidoId) {
        var partido = repositorioPartido.findById(partidoId).get();
        var datosLocal = findDatosEstadisticasByPartido(partido, true);
        var datosVisita = findDatosEstadisticasByPartido(partido, false);
        return List.of(datosLocal, datosVisita);
    }

    private List<Plantilla> findByPartido(Partido partido) {

        var equipoLocal = findEquipoByPartido(partido, true);
        var equipoVisita = findEquipoByPartido(partido, false);
        return List.of(equipoLocal, equipoVisita);
    }

    private List<GolesEquipo> findGolesByPartido(Partido partido) {

        var equipoLocal = findGolesEquipoByPartido(partido, true);
        var equipoVisita = findGolesEquipoByPartido(partido, false);
        return List.of(equipoLocal, equipoVisita);
    }

    private Marcador findMarcadorByPartido(Partido partido) {
        var equipos = findGolesByPartido(partido);
        return marcadorMapper.from(equipos.get(0), equipos.get(1));
    }

    public List<DatosEstadistica> findDatosEstadisticasByPartido(Partido partido) {
        var datosLocal = findDatosEstadisticasByPartido(partido, true);
        var datosVisita = findDatosEstadisticasByPartido(partido, false);
        return List.of(datosLocal, datosVisita);
    }

    private Plantilla findEquipoByPartido(Partido partido, boolean esLocal) {
        var equipo = filterEsLocal(partido.getEquiposParticipantes(), esLocal);
        filterPartidosParticipadosByPartido(equipo, partido);
        var jugadores = servicioJugador.findByEquipoYPartido(equipo, partido);
        equipo.setJugadores(jugadores);
        return plantillaMapper.from(equipo);
    }

    private GolesEquipo findGolesEquipoByPartido(Partido partido, boolean esLocal) {
        var equipo = filterEsLocal(partido.getEquiposParticipantes(), esLocal);
        var jugadores = servicioJugador.findGolesByEquipoYPartido(equipo, partido);
        equipo.setJugadores(jugadores);
        return equipoGolesMapper.from(equipo);
    }

    private DatosEstadistica findDatosEstadisticasByPartido(Partido partido, boolean esLocal) {
        var equipo = filterEsLocal(partido.getEquiposParticipantes(), esLocal);
        filterPartidosParticipadosByPartido(equipo, partido);
        var jugadores = servicioJugador.findByEquipoYPartido(equipo, partido);
        equipo.setJugadores(jugadores);
        return datosEstadisticaMapper.from(equipo.getPartidosParticipados().get(0));
    }

    private Equipo filterEsLocal(List<Participante> participantes, boolean esLocal) {
        return participantes.stream()
                .filter(participante -> participante.isEsLocal() == esLocal)
                .findFirst()
                .get().getEquipo();
    }

    private void filterPartidosParticipadosByPartido(Equipo equipo, Partido partido) {
        var participaciones = equipo.getPartidosParticipados()
                .stream().filter(participacion -> participacion.getPartido().equals(partido)).toList();
        equipo.setPartidosParticipados(participaciones);
    }
}
