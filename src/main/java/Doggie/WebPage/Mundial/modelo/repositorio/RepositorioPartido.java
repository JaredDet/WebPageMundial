package Doggie.WebPage.Mundial.modelo.repositorio;

import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioPartido extends JpaRepository<Partido, Long> {

    @Query(value = "SELECT * FROM Partidos " +
            "WHERE Partidos.Fase_fase_id = ?1", nativeQuery = true)
    List<Partido> findByFaseId(Long faseId);

    @Query(value = "SELECT * FROM Partidos " +
            "WHERE Partidos.Fase_fase_id != 1", nativeQuery = true)
    List<Partido> findRondaFinal();
}
