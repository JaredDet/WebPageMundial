package Doggie.WebPage.Mundial;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fase")
@Getter
@Setter
public class Fase {

    @Id
    private Long faseId;
    private String nombre;
}
