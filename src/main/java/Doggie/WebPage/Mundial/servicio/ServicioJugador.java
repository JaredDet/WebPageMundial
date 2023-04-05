package Doggie.WebPage.Mundial.servicio;

import Doggie.WebPage.Mundial.modelo.entidad.Jugador;
import Doggie.WebPage.Mundial.modelo.repositorio.RepositorioJugador;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ServicioJugador {
    private final RepositorioJugador repositorioJugador;

    public List<Jugador> findByEquipoYPartido(Long equipoId, Long partidoId) {
        return repositorioJugador.findByEquipoYPartido(equipoId, partidoId);
    }

    public List<Jugador> findGolesByEquipoYPartido(Long equipoId, Long partidoId) {
        return repositorioJugador.findJugadorGolesByEquipoYPartido(equipoId, partidoId);
    }
}
