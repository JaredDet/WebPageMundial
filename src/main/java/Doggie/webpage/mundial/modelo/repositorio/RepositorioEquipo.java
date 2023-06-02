package Doggie.WebPage.Mundial.modelo.repositorio;

import Doggie.WebPage.Mundial.modelo.entidad.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface RepositorioEquipo extends JpaRepository<Equipo, Long> {

    @Query(value = "SELECT equipos.* FROM Equipos " +
            "INNER JOIN Paises " +
            "ON Equipos.Pais_pais_id = Paises.pais_id " +
            "WHERE Paises.nombre = ?1", nativeQuery = true)
    Equipo findByNombre(String nombre);
}
