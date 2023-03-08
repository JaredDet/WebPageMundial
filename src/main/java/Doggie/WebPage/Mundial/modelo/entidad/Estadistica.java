package Doggie.WebPage.Mundial.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long estadisticaId;

    private Integer goles;
    private Integer golesEnContra;
    private Integer puntosResultados;

    private Integer remates;
    private Integer rematesAlArco;
    private Integer posesion;
    private Integer pases;
    private Integer presicionPases;
    private Integer faltas;

    @OneToMany(mappedBy = "estadistica")
    private List<Tarjeta> tarjetasAmarillas;

    @OneToMany(mappedBy = "estadistica")
    private List<Tarjeta> tarjetasRojas;

    private Integer posicionAdelantada;
    private Integer tirosEsquina;
    private Integer tirosLibres;
}
