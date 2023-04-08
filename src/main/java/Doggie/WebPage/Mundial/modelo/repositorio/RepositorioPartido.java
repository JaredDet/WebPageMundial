package Doggie.WebPage.Mundial.modelo.repositorio;

import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioPartido extends JpaRepository<Partido, Long> {

    @Query(value = "SELECT DISTINCT partidos.* FROM Grupos " +
            "INNER JOIN Equipos " +
            "ON Equipos.Grupo_grupo_id = Grupos.grupo_id " +
            "INNER JOIN Participantes " +
            "ON Participantes.Equipo_equipo_id = Equipos.equipo_id " +
            "INNER JOIN Partidos " +
            "ON Partidos.partido_id = Participantes.Partido_partido_id " +
            "WHERE Partidos.Fase_fase_id = 1 " +
            "AND Grupos.grupo_id = ?1", nativeQuery = true)
    List<Partido> findByGrupoId(Long grupoId);

    @Query(value = "SELECT * FROM Partidos " +
            "WHERE Partidos.Fase_fase_id = ?1", nativeQuery = true)
    List<Partido> findByFaseId(Long grupoId);

    @Query(value = "SELECT * FROM Partidos " +
            "WHERE Partidos.Fase_fase_id = 1", nativeQuery = true)
    List<Partido> findFaseGrupos();

    @Query(value = "SELECT * FROM Partidos " +
            "WHERE Partidos.Fase_fase_id != 1", nativeQuery = true)
    List<Partido> findRondaFinal();
}
