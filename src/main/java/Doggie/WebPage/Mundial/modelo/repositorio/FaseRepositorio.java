package Doggie.WebPage.Mundial.modelo.repositorio;

import Doggie.WebPage.Mundial.modelo.entidad.Fase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaseRepositorio extends JpaRepository<Fase, Long> {

    Fase findByNombre(String nombre);
}
