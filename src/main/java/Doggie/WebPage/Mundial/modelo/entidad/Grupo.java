package Doggie.WebPage.Mundial.modelo.entidad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "grupos")
@Getter
@Setter
@ToString
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grupoId")
    private Long grupoId;
    private String nombre;

    @OneToMany(mappedBy = "grupo")
    private List<Equipo> equipos;
}
