package Doggie.WebPage.Mundial.controlador;

import Doggie.WebPage.Mundial.dto.EquipoEnfrentamiento;
import Doggie.WebPage.Mundial.dto.EquipoGoles;
import Doggie.WebPage.Mundial.dto.Marcador;
import Doggie.WebPage.Mundial.servicio.ServicioPartido;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partidos")
@RequiredArgsConstructor
public class ControladorPartido {

    private final ServicioPartido servicioPartido;

    @GetMapping("/{partidoId}")
    public List<EquipoEnfrentamiento> partidos(@PathVariable Long partidoId) {
        return servicioPartido.findByPartido(partidoId);
    }

    @GetMapping("/{partidoId}/goles")
    public List<EquipoGoles> goles(@PathVariable Long partidoId) {
        return servicioPartido.findGolesByPartido(partidoId);
    }

    @GetMapping("/{partidoId}/marcador")
    public Marcador marcador(@PathVariable Long partidoId) {
        return servicioPartido.findMarcadorByPartido(partidoId);
    }
}
