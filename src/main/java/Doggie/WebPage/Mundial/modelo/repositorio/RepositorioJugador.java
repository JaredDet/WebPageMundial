package Doggie.WebPage.Mundial.modelo.repositorio;

import Doggie.WebPage.Mundial.modelo.entidad.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RepositorioJugador extends JpaRepository<Jugador, Long> {

    @Query(value = "SELECT DISTINCT jugadores.* FROM Goles " +
            "INNER JOIN Jugadores " +
            "ON Goles.Jugador_jugador_id = Jugadores.jugador_id " +
            "INNER JOIN Equipos " +
            "ON Jugadores.Equipo_equipo_id = Equipos.equipo_id " +
            "INNER JOIN Participantes " +
            "ON Equipos.equipo_id = Participantes.Equipo_equipo_id " +
            "WHERE Participantes.Equipo_equipo_id = ?1 " +
            "AND Participantes.Partido_partido_id = ?2", nativeQuery = true)
    List<Jugador> findJugadoresGolesByEquipoYPartido(Long equipoId, long partidoId);

    @Query(value = "SELECT jugadores.* FROM Convocados " +
            "INNER JOIN Jugadores " +
            "ON Convocados.Jugador_jugador_id = Jugadores.jugador_id " +
            "INNER JOIN Equipos " +
            "ON Jugadores.Equipo_equipo_id = Equipos.equipo_id " +
            "INNER JOIN Participantes " +
            "ON Equipos.equipo_id = Participantes.Equipo_equipo_id " +
            "WHERE Participantes.Equipo_equipo_id = ?1 " +
            "AND Participantes.Partido_partido_id = ?2", nativeQuery = true)
    List<Jugador> findJugadoresConvocadosByEquipoYPartido(Long equipoId, Long partidoId);
}
