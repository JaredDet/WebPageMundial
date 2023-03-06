package Doggie.WebPage.Mundial.modelo.repositorio;

import Doggie.WebPage.Mundial.modelo.entidad.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JugadorRepositorio extends JpaRepository<Jugador, Long> {

    Jugador findByNombre(String nombre);
}
