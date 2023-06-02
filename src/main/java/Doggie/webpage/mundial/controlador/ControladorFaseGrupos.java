package Doggie.WebPage.Mundial.controlador;

import Doggie.WebPage.Mundial.dto.ResumenPartido;
import Doggie.WebPage.Mundial.dto.Tabla;
import Doggie.WebPage.Mundial.servicio.ServicioTabla;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ronda")
@RequiredArgsConstructor
public class ControladorFaseGrupos {
    private final ServicioTabla servicioTabla;

    /**
     * Obtiene lista de partidos resumidos correspondientes a la fase de grupos del torneo.
     *
     * @return una lista de objetos ResumenPartido que representan los partidos pero resumidos de la fase de grupos del torneo.
     */

    @GetMapping("clasificatoria")
    public List<ResumenPartido> faseGrupos() {
        return servicioTabla.findFaseGrupos();
    }

    /**
     * Retorna la tabla de posiciones del grupo especificado.
     *
     * @param nombre el nombre del grupo del que se desea obtener la tabla.
     * @return la tabla de posiciones del grupo especificado.
     */

    @CrossOrigin(origins = "*", allowedHeaders = "*")

    @GetMapping("grupos/{nombre}")
    public Tabla tablaGrupo(@PathVariable String nombre) {
        return servicioTabla.findByNombreGrupo(nombre);
    }

    /**
     * Retorna una lista con todas las tablas de posiciones
     * de los grupos de la fase de grupos del torneo.
     *
     * @return una lista de objetos Tabla,
     * cada uno de ellos representando la tabla de posiciones de un grupo.
     */

    @GetMapping("grupos/tablas")
    public List<Tabla> tablas() {
        return servicioTabla.findAll();
    }
}
