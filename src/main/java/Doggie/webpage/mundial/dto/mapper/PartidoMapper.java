package Doggie.WebPage.Mundial.dto.mapper;

import Doggie.WebPage.Mundial.dto.EquipoPeticion;
import Doggie.WebPage.Mundial.dto.EstadisticaPeticion;
import Doggie.WebPage.Mundial.dto.PartidoPeticion;
import Doggie.WebPage.Mundial.modelo.entidad.Estadistica;
import Doggie.WebPage.Mundial.modelo.entidad.Jugador;
import Doggie.WebPage.Mundial.modelo.entidad.Participante;
import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import Doggie.WebPage.Mundial.modelo.repositorio.RepositorioArbitro;
import Doggie.WebPage.Mundial.modelo.repositorio.RepositorioEstadio;
import Doggie.WebPage.Mundial.modelo.repositorio.RepositorioFase;
import Doggie.WebPage.Mundial.modelo.repositorio.RepositorioFormacion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor

public class PartidoMapper {

    private final RepositorioArbitro repositorioArbitro;
    private final RepositorioEstadio repositorioEstadio;
    private final RepositorioFase repositorioFase;
    private final EquipoMapper equipoMapper;
    private final RepositorioFormacion repositorioFormacion;


    public Partido from(PartidoPeticion peticion) {

        var partido = crearPartido();

        var estadio = repositorioEstadio.findByNombre(peticion.estadio());
        var arbitro = repositorioArbitro.findByNombre(peticion.arbitro());
        var fase = repositorioFase.findByNombre(peticion.fase());

        partido.setHora(peticion.hora());
        partido.setFecha(peticion.fecha());
        partido.setFase(fase);
        partido.setArbitro(arbitro);
        partido.setEstadio(estadio);

        var peticionLocal = peticion.equipoLocal();
        var peticionVisita = peticion.equipoVisita();

        agregarParticipanteAlPartido(peticionLocal, partido, true);
        agregarParticipanteAlPartido(peticionVisita, partido, false);


        return partido;
    }

    private Partido crearPartido() {

        var partido = new Partido();
        partido.setConvocados(new ArrayList<>());
        partido.setSustituciones(new ArrayList<>());
        partido.setGoles(new ArrayList<>());
        partido.setEquiposParticipantes(new ArrayList<>());
        partido.setTarjetas(new ArrayList<>());
        return partido;
    }

    private void agregarParticipanteAlPartido(EquipoPeticion peticion, Partido partido, boolean esLocal) {

        var formacion = repositorioFormacion.findByEstrategia(peticion.formacion());

        var equipo = equipoMapper.from(peticion, partido);
        var participante = new Participante();

        participante.setPartido(partido);
        participante.setEsLocal(esLocal);
        participante.setEquipo(equipo);
        participante.setFormacion(formacion);
        agregarEstadisticasAlParticipante(peticion.estadistica(), participante);
        partido.getEquiposParticipantes().add(participante);
    }

    private void agregarEstadisticasAlParticipante(EstadisticaPeticion peticion, Participante participante) {
        var estadisticas = new Estadistica();
        estadisticas.setRemates(peticion.remates());
        estadisticas.setRematesAlArco(peticion.rematesAlArco());
        estadisticas.setPases(peticion.pases());
        estadisticas.setPosesion(peticion.posesion());
        estadisticas.setTirosEsquina(peticion.tirosEsquina());
        estadisticas.setFaltas(peticion.faltas());
        estadisticas.setPosicionesAdelantadas(peticion.posesionesAdelantadas());
        estadisticas.setPrecisionPases(peticion.precisionPases());
        participante.setEstadisticas(estadisticas);
    }
}
