package Doggie.WebPage.Mundial.controlador;

import Doggie.WebPage.Mundial.dto.PosibleBalonOro;
import Doggie.WebPage.Mundial.dto.PosibleBotaOro;
import Doggie.WebPage.Mundial.servicio.ServicioPremios;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/premios")
@RequiredArgsConstructor
public class ControladorPremios {

    private final ServicioPremios servicioPremios;

    /**
     * Obtiene al posible ganador de la bota de oro del mundial.
     *
     * @return un objeto PosibleBotaOro que contiene el nombre y los goles del jugador.
     */

    @GetMapping("/bota")
    public PosibleBotaOro botaOro() {
        return servicioPremios.posibleBotaOro();
    }

    /**
     * Obtiene al posible ganador del balón de oro del mundial.
     *
     * @return un objeto PosibleBalonOro que contiene el nombre y las veces mvp del jugador.
     */

    @GetMapping("/mvp")
    public PosibleBalonOro balonOro() {
        return servicioPremios.posibleBalonOro();
    }
}
