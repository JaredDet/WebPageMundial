package Doggie.WebPage.Mundial.modelo.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "arbitro")
@Getter
@Setter
public class Arbitro {

    @Id
    private Long arbitroId;
    private String nombre;
}
