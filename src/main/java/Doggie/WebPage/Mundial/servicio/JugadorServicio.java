package Doggie.WebPage.Mundial.servicio;

import Doggie.WebPage.Mundial.dto.JugadorTitular;
import Doggie.WebPage.Mundial.dto.mapper.JugadorTitularMapper;
import Doggie.WebPage.Mundial.modelo.entidad.Equipo;
import Doggie.WebPage.Mundial.modelo.entidad.Jugador;
import Doggie.WebPage.Mundial.modelo.repositorio.JugadorRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JugadorServicio {

    private final JugadorRepositorio jugadorRepositorio;
    private final JugadorTitularMapper jugadorTitularMapper;

    public Jugador findJugadorByNombre(String nombre) {
        return jugadorRepositorio.findByNombre(nombre);
    }

    public JugadorTitular findJugadorTitularByNombre(String nombre) {
        var jugador = findJugadorByNombre(nombre);
        return jugadorTitularMapper.toJugadorTitular(jugador);
    }

    public List<Jugador> findJugadorTitularByEquipo(Equipo equipo) {
        return jugadorRepositorio.findByEquipo(equipo);
    }
}
