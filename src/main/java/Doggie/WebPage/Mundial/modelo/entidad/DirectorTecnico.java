package Doggie.WebPage.Mundial.modelo.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "director_tecnico")
@Getter
@Setter
public class DirectorTecnico {

    @Id
    private Long directorTecnicoId;

    @Column(unique = true)
    private String nombre;
}
