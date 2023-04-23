package Doggie.WebPage.Mundial.modelo.repositorio;

import Doggie.WebPage.Mundial.modelo.entidad.Posicion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioPosicion extends JpaRepository<Posicion, Long> {

    Posicion findByAbreviacion(String abreviacion);
}