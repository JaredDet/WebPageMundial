package Doggie.WebPage;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
@Entity
@Table
@Getter
@Setter
@NonNullByDefault

public class Vehiculo implements Producto {

    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    private TipoVehiculo tipoVehiculo;
    @ManyToOne
    private Marca marca;
    @ManyToOne
    private Modelo modelo;
    @Nullable
    private String url;
    @Column(columnDefinition = "integer default '0'")
    private int precio;
    @Nullable
    private String nombre;
    @Nullable
    private String anio;
    @Column(columnDefinition = "integer default '0'")
    private int kilometros;
    @Nullable
    private String color;
}
