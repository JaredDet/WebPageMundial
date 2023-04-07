package Doggie.WebPage.Mundial.modelo.entidad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "fases")
@Getter
@Setter
@ToString
public class Fase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faseId")
    private Long faseId;
    private String nombre;

    @OneToMany(mappedBy = "fase")
    private List<Partido> partidos;
}
