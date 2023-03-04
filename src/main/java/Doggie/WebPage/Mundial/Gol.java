package Doggie.WebPage.Mundial;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "gol")
@Getter
@Setter
public class Gol {

    @Id
    @Column(name = "golId")
    private Long golId;

    @ManyToOne
    private Estadistica estadistica;

    @ManyToOne
    private JugadorEnCancha autor;

    private int minuto;
}
