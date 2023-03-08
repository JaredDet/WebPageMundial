package Doggie.WebPage.Mundial.modelo.repositorio;

import Doggie.WebPage.Mundial.modelo.entidad.Equipo;
import Doggie.WebPage.Mundial.modelo.entidad.Fase;
import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidoRepositorio extends JpaRepository<Partido, Long> {

    List<Partido> findByFase(Fase fase);

    @Query(value = "SELECT DISTINCT partido.* FROM Partido " +
            "INNER JOIN Rel_Partidos_Equipos " +
            "ON Rel_Partidos_Equipos.partido_partido_id = Partido.partido_id " +
            "INNER JOIN Equipo " +
            "ON Equipo.equipo_id = Rel_Partidos_Equipos.equipo_equipo_id " +
            "INNER JOIN Grupo " +
            "ON Grupo.grupo_id = Equipo.grupo_grupo_id " +
            "INNER JOIN Fase " +
            "ON Fase.fase_id = Partido.fase_fase_id " +
            "WHERE Fase.fase_id = ?1 " +
            "AND Grupo.grupo_id = ?2", nativeQuery = true)
    List<Partido> findByFaseAndGrupo(Long faseId, Long grupoId);

    @Query(value = "SELECT DISTINCT partido.* FROM PARTIDO " +
            "INNER JOIN Rel_Partidos_Equipos " +
            "ON Rel_Partidos_Equipos.partido_partido_id = Partido.partido_id " +
            "INNER JOIN EQUIPO " +
            "ON Equipo.equipo_id = Rel_Partidos_Equipos.equipo_equipo_id " +
            "WHERE equipo.equipo_id = ?1", nativeQuery = true)
    List<Partido> findByEquipo(Long equipoId);
}
