package Doggie.WebPage;

import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface VehiculoMapper {
    List<VehiculoPublicacion> toVehiculosPublicacion(List<Vehiculo> vehiculos);
}
