package Doggie.WebPage.Mundial.modelo.entidad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "estadistica")
@Getter
@Setter
public class Estadistica {

    @Id
    private Long estadisticaId;

    @ManyToOne
    private Equipo equipo;

    @OneToMany(mappedBy = "estadistica")
    private List<Gol> goles;

    private int remates;
    private int rematesAlArco;
    private int posesion;
    private int pases;
    private int presicionPases;
    private int faltas;

    @OneToMany(mappedBy = "estadistica")
    private List<Tarjeta> tarjetasAmarillas;

    @OneToMany(mappedBy = "estadistica")
    private List<Tarjeta> tarjetasRojas;

    private int posicionAdelantada;
    private int tirosEsquina;
    private int tirosLibres;
}
