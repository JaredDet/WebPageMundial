package Doggie.WebPage.Mundial.modelo.repositorio;

import Doggie.WebPage.Mundial.modelo.entidad.Posicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepositorioPosicion extends JpaRepository<Posicion, Long> {

    @Query(value = "SELECT posiciones.* FROM Posiciones " +
            "INNER JOIN Abreviaturas " +
            "ON Posiciones.abreviatura_abreviatura_id = Abreviaturas.abreviatura_id " +
            "WHERE Abreviaturas.abreviatura_id = ?1 " +
            "AND Posiciones.lado = ?2", nativeQuery = true)
    Posicion findByAbreviaturaYLado(Long abreviaturaID, String lado);
}
