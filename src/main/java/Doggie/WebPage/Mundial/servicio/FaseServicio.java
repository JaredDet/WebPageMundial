package Doggie.WebPage.Mundial.servicio;

import Doggie.WebPage.Mundial.modelo.entidad.Fase;
import Doggie.WebPage.Mundial.modelo.repositorio.FaseRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FaseServicio {

    private final FaseRepositorio faseRepositorio;

    public Fase findByNombre(String nombre) {
        return faseRepositorio.findByNombre(nombre);
    }
}
