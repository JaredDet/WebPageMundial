package Doggie.WebPage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioModelo extends JpaRepository<Modelo, Long> {

    Modelo findByNombre(String nombre);
}
