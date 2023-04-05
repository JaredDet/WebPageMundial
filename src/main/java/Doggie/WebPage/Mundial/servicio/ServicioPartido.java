package Doggie.WebPage.Mundial.servicio;

import Doggie.WebPage.Mundial.dto.EquipoEnfrentamiento;
import Doggie.WebPage.Mundial.dto.EquipoGoles;
import Doggie.WebPage.Mundial.dto.Marcador;
import Doggie.WebPage.Mundial.dto.mapper.*;
import Doggie.WebPage.Mundial.modelo.entidad.Equipo;
import Doggie.WebPage.Mundial.modelo.entidad.Jugador;
import Doggie.WebPage.Mundial.modelo.entidad.Participante;
import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import Doggie.WebPage.Mundial.modelo.repositorio.RepositorioEquipo;
import Doggie.WebPage.Mundial.modelo.repositorio.RepositorioPartido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ServicioPartido {

    private final ServicioJugador servicioJugador;

    private final EquipoEnfrentamientoMapper equipoEnfrentamientoMapper;
    private final EquipoGolesMapper equipoGolesMapper;
    private final MarcadorMapper marcadorMapper;
    private final RepositorioPartido repositorioPartido;

    public List<EquipoEnfrentamiento> findByPartido(Long partidoId) {

        var partido = repositorioPartido.findById(partidoId).get();

        var equipoLocal = findEquipoByPartido(partido, true);
        var equipoVisita = findEquipoByPartido(partido, false);
        return List.of(equipoLocal, equipoVisita);
    }

    public List<EquipoGoles> findGolesByPartido(Long partidoId) {

        var partido = repositorioPartido.findById(partidoId).get();

        var equipoLocal = findGolesEquipoByPartido(partido, true);
        var equipoVisita = findGolesEquipoByPartido(partido, false);
        return List.of(equipoLocal, equipoVisita);
    }

    public Marcador findMarcadorByPartido(Long partidoId) {
        var equipos = findGolesByPartido(partidoId);
        return marcadorMapper.from(equipos.get(0), equipos.get(1));
    }

    private EquipoEnfrentamiento findEquipoByPartido(Partido partido, boolean esLocal) {
        var equipo = filterEsLocal(partido.getEquiposParticipantes(), esLocal);
        var jugadores = servicioJugador.findByEquipoYPartido(equipo.getEquipoId(), partido.getPartidoId());
        equipo.setJugadores(jugadores);
        var participante = filterByEquipo(partido.getEquiposParticipantes(), equipo);
        participante.setEquipo(equipo);
        return equipoEnfrentamientoMapper.from(participante);
    }

    private EquipoGoles findGolesEquipoByPartido(Partido partido, boolean esLocal) {
        var equipo = filterEsLocal(partido.getEquiposParticipantes(), esLocal);
        var jugadores = servicioJugador.findGolesByEquipoYPartido(equipo.getEquipoId(), partido.getPartidoId());
        equipo.setJugadores(jugadores);
        return equipoGolesMapper.from(equipo);
    }

    private Equipo filterEsLocal(List<Participante> participantes, boolean esLocal) {
        return participantes.stream()
                .filter(participante -> participante.isEsLocal() == esLocal)
                .findFirst()
                .get().getEquipo();
    }

    private Participante filterByEquipo(List<Participante> participantes, Equipo equipo) {
        return participantes.stream()
                .filter(participante -> participante.getEquipo().equals(equipo))
                .findFirst().get();
    }
}
