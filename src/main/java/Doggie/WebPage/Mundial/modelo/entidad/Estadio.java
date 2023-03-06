package Doggie.WebPage.Mundial.modelo.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "estadio")
@Getter
@Setter
public class Estadio {

    @Id
    private Long estadioId;
    private String nombre;
}
