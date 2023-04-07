package Doggie.WebPage.Mundial.controlador;

import Doggie.WebPage.Mundial.dto.*;
import Doggie.WebPage.Mundial.servicio.ServicioPartido;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partidos")
@RequiredArgsConstructor
public class ControladorPartido {

    private final ServicioPartido servicioPartido;

    @GetMapping("{partidoId}")
    public DatosPartido partido(@PathVariable Long partidoId) {
        return servicioPartido.datosPartido(partidoId);
    }

    @GetMapping("/{partidoId}/plantillas")
    public List<Plantilla> plantillas(@PathVariable Long partidoId) {
        return servicioPartido.findByPartido(partidoId);
    }

    @GetMapping("/{partidoId}/goles")
    public List<GolesEquipo> goles(@PathVariable Long partidoId) {
        return servicioPartido.findGolesByPartido(partidoId);
    }

    @GetMapping("/{partidoId}/marcador")
    public Marcador marcador(@PathVariable Long partidoId) {
        return servicioPartido.findMarcadorByPartido(partidoId);
    }

    @GetMapping("/{partidoId}/estadisticas")
    public List<DatosEstadistica> estadisticas(@PathVariable Long partidoId) {
        return servicioPartido.findDatosEstadisticasByPartido(partidoId);
    }
}
