package Doggie.WebPage.Mundial.controlador;

import Doggie.WebPage.Mundial.dto.Ronda;
import Doggie.WebPage.Mundial.servicio.ServicioRonda;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ronda")
@RequiredArgsConstructor
public class ControladorRondaClasificatoria {

    private final ServicioRonda servicioRonda;

    @GetMapping("/grupos")
    public List<Ronda> grupos() {
        return servicioRonda.findByFase(1L);
    }
}
