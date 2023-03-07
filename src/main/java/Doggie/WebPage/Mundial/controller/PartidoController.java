package Doggie.WebPage.Mundial.controller;

import Doggie.WebPage.Mundial.modelo.entidad.Grupo;
import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import Doggie.WebPage.Mundial.servicio.GrupoServicio;
import Doggie.WebPage.Mundial.servicio.PartidoServicio;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PartidoController {

    private final PartidoServicio partidoServicio;

    @GetMapping("/partidos")
    public List<Partido> partidos(@RequestParam String nombreFase, @RequestParam(required = false) String nombreGrupo) {
        return partidoServicio.findByNombreFaseAndNombreGrupo(nombreFase, nombreGrupo);
    }
}
