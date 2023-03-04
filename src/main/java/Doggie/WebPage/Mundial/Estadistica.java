package Doggie.WebPage.Mundial;

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
    private Grupo grupo;

    @OneToMany(mappedBy = "golId")
    private List<Gol> goles;

    private int remates;
    private int rematesAlArco;
    private int posesion;
    private int pases;
    private int presicionPases;
    private int faltas;

    @OneToMany(mappedBy = "tarjetaId")
    private List<Tarjeta> tarjetasAmarillas;

    @OneToMany(mappedBy = "tarjetaId")
    private List<Tarjeta> tarjetasRojas;

    private int posicionAdelantada;
    private int tirosEsquina;
    private int tirosLibres;
}
