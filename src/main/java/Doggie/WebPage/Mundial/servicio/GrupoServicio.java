package Doggie.WebPage.Mundial.servicio;

import Doggie.WebPage.Mundial.modelo.entidad.Grupo;
import Doggie.WebPage.Mundial.modelo.repositorio.GrupoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GrupoServicio {

    private final GrupoRepositorio grupoRepositorio;

    public Grupo findGrupo(String nombreGrupo) {
        return grupoRepositorio.findByNombre(nombreGrupo);
    }
}
