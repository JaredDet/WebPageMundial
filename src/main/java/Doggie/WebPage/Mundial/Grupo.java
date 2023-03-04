package Doggie.WebPage.Mundial;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "grupo")
@Getter
@Setter
public class Grupo {

    @Id
    private Long grupoId;
    private String nombre;
    @OneToMany(mappedBy = "equipoId")
    private List<Equipo> equipos;
}
