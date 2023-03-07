package Doggie.WebPage.Mundial.controller;

import Doggie.WebPage.Mundial.dto.EquipoTitular;
import Doggie.WebPage.Mundial.modelo.entidad.Equipo;
import Doggie.WebPage.Mundial.servicio.EquipoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EquipoController {

    private final EquipoServicio equipoServicio;

    @GetMapping("/equipo_titular")
    public EquipoTitular equipoTitular(@RequestParam String nombre) {
        return equipoServicio.findEquipoTitular(nombre);
    }

    @GetMapping("/equipo")
    public Equipo equipo(@RequestParam String nombre) {
        return equipoServicio.findEquipo(nombre);
    }
}
