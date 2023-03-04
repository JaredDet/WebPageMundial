package Doggie.WebPage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioMarca extends JpaRepository<Marca, Long> {

    Marca findByNombre(String nombre);
}
