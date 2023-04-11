package Doggie.WebPage.Mundial.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "grupos")
@Getter
@Setter
@ToString(exclude = {"equipos"})
@EqualsAndHashCode
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grupoId")
    private Long grupoId;
    private String nombre;

    @JsonManagedReference
    @OneToMany(mappedBy = "grupo")
    private List<Equipo> equipos;
}
