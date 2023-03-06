package Doggie.WebPage.Mundial.servicio;

import Doggie.WebPage.Mundial.dto.EquipoTitular;
import Doggie.WebPage.Mundial.dto.mapper.EquipoTitularMapper;
import Doggie.WebPage.Mundial.modelo.entidad.Equipo;
import Doggie.WebPage.Mundial.modelo.repositorio.EquipoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipoServicio {

    private final EquipoRepositorio equipoRepositorio;
    private final JugadorServicio jugadorServicio;
    private final EquipoTitularMapper equipoTitularMapper;

    public Equipo findEquipo(String nombreEquipo) {
        var equipo = equipoRepositorio.findByNombre(nombreEquipo);
        equipo.setJugadores(jugadorServicio.findJugadorTitularByEquipo(equipo));
        return equipo;
    }

    public EquipoTitular findEquipoTitular(String nombreEquipo) {
        var equipo = findEquipo(nombreEquipo);
        return equipoTitularMapper.toEquipoTitular(equipo);
    }
}
