package Doggie.WebPage.Mundial.modelo.repositorio;

import Doggie.WebPage.Mundial.modelo.entidad.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioGrupo extends JpaRepository<Grupo, Long> {

    Grupo findByNombre(String nombre);
}
