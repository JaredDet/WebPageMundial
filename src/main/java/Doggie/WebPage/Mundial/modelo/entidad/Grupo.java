package Doggie.WebPage.Mundial.modelo.entidad;

import jakarta.persistence.*;
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
    @Column(unique = true)
    private String nombre;
    @OneToMany(mappedBy = "equipoId")
    private List<Equipo> equipos;
}
