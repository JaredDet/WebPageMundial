package Doggie.WebPage.Mundial.modelo.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "formacion")
@Getter
@Setter
public class Formacion {

    @Id
    private Long equipoId;
    @Column(unique = true)
    private String secuencia;
}
