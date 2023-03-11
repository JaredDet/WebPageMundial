package Doggie.WebPage.Mundial.modelo.repositorio;

import Doggie.WebPage.Mundial.modelo.entidad.Equipo;
import Doggie.WebPage.Mundial.modelo.entidad.EquipoEnPartido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipoEnPartidoRepositorio extends JpaRepository<EquipoEnPartido, EquipoEnPartido.EquipoEnPartidoPK> {

    EquipoEnPartido findByEquipo(Equipo equipo);

    @Query(value = "SELECT DISTINCT Equipo_En_Partido.* FROM Equipo_En_Partido " +
            "WHERE Equipo_En_Partido.equipo_equipo_id = ?1 " +
            "AND Equipo_En_Partido.partido_partido_id = ?2", nativeQuery = true)
    EquipoEnPartido findByEquipoAndPartido(Long equipoId, Long partidoId);
}
