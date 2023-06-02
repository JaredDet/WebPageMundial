package Doggie.WebPage.Mundial.modelo.repositorio;

import Doggie.WebPage.Mundial.modelo.entidad.Abreviatura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioAbreviatura extends JpaRepository<Abreviatura, Long> {

    Abreviatura findByNombre(String nombre);
}
