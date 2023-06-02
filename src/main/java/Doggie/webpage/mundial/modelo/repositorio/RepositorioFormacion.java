package Doggie.WebPage.Mundial.modelo.repositorio;

import Doggie.WebPage.Mundial.modelo.entidad.Formacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioFormacion extends JpaRepository<Formacion, Long> {

    Formacion findByEstrategia(String estrategia);
}

