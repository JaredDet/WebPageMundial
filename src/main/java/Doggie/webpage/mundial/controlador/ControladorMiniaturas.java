package Doggie.WebPage.Mundial.controlador;

import Doggie.WebPage.Mundial.dto.ResumenPartido;
import Doggie.WebPage.Mundial.servicio.ServicioRonda;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ronda")
@RequiredArgsConstructor
public class ControladorMiniaturas {

    private final ServicioRonda servicioEtapa;

    /**
     * Obtiene la lista de partidos resumidos correspondientes a una etapa específica del torneo.
     *
     * @param faseId el ID de la fase de la que se desean obtener los partidos resumidos.
     * @return una lista de objetos ResumenPartido que representan los partidos correspondientes a la fase especificada.
     */

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("{faseId}")
    public List<ResumenPartido> fase(@PathVariable Long faseId) {
        return servicioEtapa.findByFase(faseId);
    }

    /**
     * Obtiene la lista de partidos resumidos de la ronda final del torneo.
     *
     * @return una lista de objetos ResumenPartido que representan los partidos de la ronda final del torneo.
     */

    @GetMapping("/torneo")
    public List<ResumenPartido> torneo() {
        return servicioEtapa.findRondaFinal();
    }
}
