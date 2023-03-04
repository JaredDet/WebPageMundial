package Doggie.WebPage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioVehiculo extends JpaRepository<Vehiculo, Long> {
    List<Vehiculo> findByTipoVehiculo(TipoVehiculo tipoVehiculo);

    List<Vehiculo> findByTipoVehiculoAndMarca(TipoVehiculo tipoVehiculo, Marca marca);

    List<Vehiculo> findByTipoVehiculoAndMarcaAndModelo(TipoVehiculo tipoVehiculo, Marca marca, Modelo modelo);
}
