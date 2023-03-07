package Doggie.WebPage.Mundial.controller;

import Doggie.WebPage.Mundial.modelo.entidad.Grupo;
import Doggie.WebPage.Mundial.servicio.GrupoServicio;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GrupoController {

    private final GrupoServicio grupoServicio;

    @SneakyThrows
    @GetMapping("/grupo")
    public Grupo grupo(@RequestParam String nombre) {
        var grupo = grupoServicio.findGrupo(nombre);
        System.out.println(new ObjectMapper().writeValueAsString(grupo));
        return grupo;
    }
}
