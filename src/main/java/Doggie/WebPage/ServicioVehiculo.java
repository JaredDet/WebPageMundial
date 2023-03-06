package Doggie.WebPage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ServicioVehiculo {

    private final RepositorioVehiculo repositorioVehiculo;
    private final RepositorioMarca repositorioMarca;
    private final RepositorioModelo repositorioModelo;
    private final VehiculoMapper vehiculoMapper;


    public List<Vehiculo> getAll() {
        return repositorioVehiculo.findAll();
    }

    public List<VehiculoPublicacion> getVehiculos(TipoVehiculo tipoVehiculo, String nombreMarca, String nombreModelo) {

        if (nombreMarca == null) {
            return vehiculoMapper.toVehiculosPublicacion(repositorioVehiculo.findByTipoVehiculo(tipoVehiculo));
        }

        var marca = repositorioMarca.findByNombre(nombreMarca);

        if (nombreModelo == null) {
            return vehiculoMapper.toVehiculosPublicacion(repositorioVehiculo.findByTipoVehiculoAndMarca(tipoVehiculo, marca));
        }

        var modelo = repositorioModelo.findByNombre(nombreModelo);
        return vehiculoMapper.toVehiculosPublicacion(repositorioVehiculo.findByTipoVehiculoAndMarcaAndModelo(tipoVehiculo, marca, modelo));
    }
}
