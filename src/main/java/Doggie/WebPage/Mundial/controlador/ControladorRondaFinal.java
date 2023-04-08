package Doggie.WebPage.Mundial.controlador;

import Doggie.WebPage.Mundial.dto.Ronda;
import Doggie.WebPage.Mundial.servicio.ServicioRonda;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ronda")
@RequiredArgsConstructor
public class ControladorRondaFinal {

    private final ServicioRonda servicioRondaFinal;

    @GetMapping("{faseId}")
    public List<Ronda> fase(@PathVariable Long faseId) {
        return servicioRondaFinal.findByFase(faseId);
    }

    @GetMapping("/torneo")
    public List<Ronda> torneo() {
        return servicioRondaFinal.findRondaFinal();
    }
}
