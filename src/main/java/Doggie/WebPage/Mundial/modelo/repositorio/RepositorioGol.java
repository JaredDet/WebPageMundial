package Doggie.WebPage.Mundial.modelo.repositorio;

import Doggie.WebPage.Mundial.modelo.entidad.Gol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioGol extends JpaRepository<Gol, Long> {

    @Query(value = "SELECT goles.* FROM Partidos " +
            "INNER JOIN Goles " +
            "ON Goles.Partido_partido_id = Partidos.partido_id " +
            "INNER JOIN Jugadores " +
            "ON Goles.Jugador_jugador_id = Jugadores.jugador_id " +
            "WHERE Goles.Jugador_jugador_id = ?1 " +
            "AND Goles.Partido_partido_id = ?2 " +
            "AND Goles.penal = true " +
            "AND Goles.minuto IS null", nativeQuery = true)
    List<Gol> findPenales(Long jugadorId, long partidoId);

    @Query(value = "SELECT goles.* FROM Partidos " +
            "INNER JOIN Goles " +
            "ON Goles.Partido_partido_id = Partidos.partido_id " +
            "INNER JOIN Jugadores " +
            "ON Goles.Jugador_jugador_id = Jugadores.jugador_id " +
            "WHERE Goles.Jugador_jugador_id = ?1 " +
            "AND Goles.Partido_partido_id = ?2 " +
            "AND Goles.minuto IS NOT null", nativeQuery = true)
    List<Gol> findGoles(Long jugadorId, long partidoId);
}
