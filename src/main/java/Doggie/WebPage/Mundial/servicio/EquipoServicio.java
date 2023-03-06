package Doggie.WebPage.Mundial.servicio;

import Doggie.WebPage.Mundial.dto.EquipoTitular;
import Doggie.WebPage.Mundial.dto.mapper.EquipoTitularMapper;
import Doggie.WebPage.Mundial.modelo.entidad.Equipo;
import Doggie.WebPage.Mundial.modelo.repositorio.EquipoRepositorio;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipoServicio {

    private final EquipoRepositorio equipoRepositorio;
    private final EquipoTitularMapper equipoTitularMapper;

    public Equipo findEquipo(String nombreEquipo) {
        return equipoRepositorio.findByNombre(nombreEquipo);
    }

    public EquipoTitular findEquipoTitular(String nombreEquipo) {
        var equipo = findEquipo(nombreEquipo);
        return equipoTitularMapper.toEquipoTitular(equipo);
    }
}
