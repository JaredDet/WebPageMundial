package Doggie.WebPage.Mundial.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "fases")
@Getter
@Setter
@ToString(exclude = {"partidos"})
@EqualsAndHashCode
public class Fase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faseId")
    private Long faseId;
    private String nombre;

    @JsonManagedReference
    @OneToMany(mappedBy = "fase")
    private List<Partido> partidos;
}
