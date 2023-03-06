package Doggie.WebPage.Mundial.controller;

import Doggie.WebPage.Mundial.dto.JugadorTitular;
import Doggie.WebPage.Mundial.servicio.JugadorServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor

public class JugadorController {
    private final JugadorServicio jugadorServicio;

    @GetMapping("/jugador_titular")
    public JugadorTitular jugadorTitular(@RequestParam String nombre) {
        return jugadorServicio.findJugadorTitularByNombre(nombre);
    }
}
