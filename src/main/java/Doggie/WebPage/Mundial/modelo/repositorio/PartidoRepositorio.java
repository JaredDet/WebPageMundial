package Doggie.WebPage.Mundial.modelo.repositorio;

import Doggie.WebPage.Mundial.modelo.entidad.Fase;
import Doggie.WebPage.Mundial.modelo.entidad.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidoRepositorio extends JpaRepository<Partido, Long> {

    List<Partido> findByFase(Fase fase);

    @Query(value = "SELECT DISTINCT partido.* FROM Grupo " +
            "INNER JOIN Equipo " +
            "ON Grupo.grupo_id = Equipo.grupo_grupo_id " +
            "INNER JOIN Equipo_En_Partido " +
            "ON Equipo.equipo_id = Equipo_En_Partido.equipo_equipo_id " +
            "INNER JOIN Partido " +
            "ON Equipo_En_Partido.partido_partido_id = Partido.partido_id " +
            "INNER JOIN Fase " +
            "ON Partido.fase_fase_id = Fase.fase_id " +
            "WHERE Fase.fase_id = ?1 " +
            "AND Grupo.grupo_id = ?2", nativeQuery = true)
    List<Partido> findByFaseAndGrupo(Long faseId, Long grupoId);

    @Query(value = "SELECT DISTINCT partido.* FROM Partido " +
            "INNER JOIN Equipo_En_Partido " +
            "ON Equipo_En_Partido.partido_partido_id = Partido.partido_id " +
            "INNER JOIN Equipo " +
            "ON Equipo.equipo_id = Equipo_En_Partido.equipo_equipo_id " +
            "WHERE Equipo.equipo_id = ?1", nativeQuery = true)
    List<Partido> findByEquipo(Long equipoId);
}
