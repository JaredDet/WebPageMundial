package Doggie.WebPage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static Doggie.WebPage.TipoVehiculo.*;

@RestController
@RequestMapping("/api")
public class ControllerVehiculo {

    private final ServicioVehiculo servicio;

    @Autowired
    public ControllerVehiculo(ServicioVehiculo servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/motos")
    public List<VehiculoPublicacion> getMotos(@RequestParam(required = false) String marca, @RequestParam(required = false) String modelo) {
        return servicio.getVehiculos(MOTO, marca, modelo);
    }

    @GetMapping("/autos")
    public List<VehiculoPublicacion> getAutos(@RequestParam(required = false) String marca, @RequestParam(required = false) String modelo) {
        return servicio.getVehiculos(AUTO, marca, modelo);
    }

    @GetMapping("/camiones")
    public List<VehiculoPublicacion> getCamiones(@RequestParam(required = false) String marca, @RequestParam(required = false) String modelo) {
        return servicio.getVehiculos(CAMION, marca, modelo);
    }

    @GetMapping("/camionetas")
    public List<VehiculoPublicacion> getCamionetas(@RequestParam(required = false) String marca, @RequestParam(required = false) String modelo) {
        return servicio.getVehiculos(CAMIONETA, marca, modelo);
    }
}
