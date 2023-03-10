package Doggie.WebPage.Mundial.servicio;

import Doggie.WebPage.Mundial.dto.EquipoTablaFaseGrupos;
import Doggie.WebPage.Mundial.dto.PartidoResultados;
import Doggie.WebPage.Mundial.dto.mapper.AgrupaRepetidosMapper;
import Doggie.WebPage.Mundial.dto.mapper.EquipoTablaFaseGruposMapper;
import Doggie.WebPage.Mundial.dto.mapper.PartidoResultadosMapper;
import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import Doggie.WebPage.Mundial.modelo.repositorio.PartidoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartidoServicio {

    private final PartidoRepositorio partidoRepositorio;

    private final FaseServicio faseServicio;

    private final GrupoServicio grupoServicio;

    private final EquipoTablaFaseGruposMapper equipoTablaFaseGruposMapper;

    private final EquipoServicio equipoServicio;

    private final PartidoResultadosMapper partidoResultadosMapper;

    private final AgrupaRepetidosMapper agrupaRepetidosMapper;


    public List<Partido> findByNombreFaseAndNombreGrupo(String nombreFase, String nombreGrupo) {
        var fase = faseServicio.findByNombre(nombreFase);
        var grupo = grupoServicio.findGrupo(nombreGrupo);

        if (grupo == null) {
            return partidoRepositorio.findByFase(fase);
        }
        return partidoRepositorio.findByFaseAndGrupo(fase.getFaseId(), grupo.getGrupoId());
    }

    public List<PartidoResultados> findByNombreEquipo(String nombreEquipo) {

        if (nombreEquipo == null) {
            return partidoRepositorio.findAll().stream().map(partidoResultadosMapper::toPartidoResultados).toList();
        }
        var equipo = equipoServicio.findEquipo(nombreEquipo);
        var partidos = partidoRepositorio.findByEquipo(equipo.getEquipoId());
        return partidos.stream().map(partidoResultadosMapper::toPartidoResultados).toList();
    }

    public List<EquipoTablaFaseGrupos> findEquipos(String nombreFase, String nombreGrupo) {
        var partidos = findByNombreFaseAndNombreGrupo(nombreFase, nombreGrupo);

        var relaciones = partidos.stream().map(Partido::getEquipos)
                .flatMap(Collection::stream).toList();

        var equiposNoAgrupados = relaciones.stream()
                .map(equipoTablaFaseGruposMapper::toEquipoPartido).toList();

        var equiposAgrupadosRepetidos = equiposNoAgrupados.stream().collect(Collectors.groupingBy(EquipoTablaFaseGrupos::nombre));

        return agrupaRepetidosMapper.mapToEquiposSinRepetir(equiposAgrupadosRepetidos);
    }
}
