package Doggie.WebPage.Mundial.servicio;

import Doggie.WebPage.Mundial.dto.EquipoAlineacion;
import Doggie.WebPage.Mundial.dto.EquipoTitular;
import Doggie.WebPage.Mundial.dto.mapper.EquipoAlineacionMapper;
import Doggie.WebPage.Mundial.dto.mapper.EquipoTitularMapper;
import Doggie.WebPage.Mundial.modelo.entidad.Equipo;
import Doggie.WebPage.Mundial.modelo.repositorio.EquipoRepositorio;
import Doggie.WebPage.Mundial.modelo.repositorio.EquipoEnPartidoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipoServicio {

    private final EquipoRepositorio equipoRepositorio;
    private final EquipoTitularMapper equipoTitularMapper;
    private final EquipoAlineacionMapper equipoAlineacionMapper;
    private final EquipoEnPartidoRepositorio equipoEnPartidoRepositorio;


    public Equipo findEquipo(String nombreEquipo) {
        return equipoRepositorio.findByNombre(nombreEquipo);
    }

    public EquipoTitular findEquipoTitular(String nombreEquipo) {
        var equipo = findEquipo(nombreEquipo);
        return equipoTitularMapper.toEquipoTitular(equipo);
    }

    public List<EquipoAlineacion> findByEquipos(String nombreEquipoLocal, String nombreEquipoVisita, Long partidoId) {
        var local = findByEquipoAndPartido(nombreEquipoLocal, partidoId);
        var visita = findByEquipoAndPartido(nombreEquipoVisita, partidoId);
        return List.of(local, visita);
    }

    private EquipoAlineacion findByEquipoAndPartido(String nombreEquipo, Long partidoId) {
        var equipo = equipoRepositorio.findByNombre(nombreEquipo);
        var equipos = equipoEnPartidoRepositorio.findByEquipoAndPartido(equipo.getEquipoId(), partidoId);
        return equipoAlineacionMapper.toEquipoAlineacion(equipos);
    }
}
