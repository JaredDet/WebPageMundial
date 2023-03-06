package Doggie.WebPage.Mundial.modelo.repositorio;

import Doggie.WebPage.Mundial.modelo.entidad.Equipo;
import Doggie.WebPage.Mundial.modelo.entidad.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JugadorRepositorio extends JpaRepository<Jugador, Long> {

    List<Jugador> findByEquipo(Equipo equipo);

    Jugador findByNombre(String nombre);
}
