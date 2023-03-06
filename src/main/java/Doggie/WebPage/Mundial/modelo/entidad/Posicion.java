package Doggie.WebPage.Mundial.modelo.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "posicion")
@Getter
@Setter
public class Posicion {

    @Id
    private Long posicionId;
    private String nombre;
}
