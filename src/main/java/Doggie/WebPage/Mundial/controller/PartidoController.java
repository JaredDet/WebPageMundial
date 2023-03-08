package Doggie.WebPage.Mundial.controller;

import Doggie.WebPage.Mundial.dto.EquipoTablaFaseGrupos;
import Doggie.WebPage.Mundial.dto.PartidoResultados;
import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import Doggie.WebPage.Mundial.servicio.PartidoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PartidoController {

    private final PartidoServicio partidoServicio;

    @GetMapping("/partidos")
    public List<Partido> partidos(@RequestParam String nombreFase, @RequestParam(required = false) String nombreGrupo) {
        return partidoServicio.findByNombreFaseAndNombreGrupo(nombreFase, nombreGrupo);
    }

    @GetMapping("/tabla_equipos")
    public List<EquipoTablaFaseGrupos> equiposPartidos(@RequestParam String nombreFase, @RequestParam(required = false) String nombreGrupo) {
        return partidoServicio.findEquipos(nombreFase, nombreGrupo);
    }

    @GetMapping("/partidos/equipos")
    public List<PartidoResultados> partidos(@RequestParam(required = false) String nombre) {
        return partidoServicio.findByNombreEquipo(nombre);
    }
}
