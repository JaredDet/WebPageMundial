package Doggie.WebPage.Mundial.controlador;

import Doggie.WebPage.Mundial.dto.Ronda;
import Doggie.WebPage.Mundial.dto.Tabla;
import Doggie.WebPage.Mundial.servicio.ServicioRonda;
import Doggie.WebPage.Mundial.servicio.ServicioTabla;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ronda")
@RequiredArgsConstructor
public class ControladorRondaClasificatoria {
    private final ServicioTabla servicioTabla;

    @GetMapping("clasificatoria")
    public List<Ronda> clasificatoria() {
        return servicioTabla.findFaseGrupos();
    }

    @GetMapping("grupos/{nombre}")
    public Tabla tablaGrupo(@PathVariable String nombre) {
        return servicioTabla.findByNombreGrupo(nombre);
    }

    @GetMapping("grupos/tablas")
    public List<Tabla> tablas() {
        return servicioTabla.findAll();
    }
}
