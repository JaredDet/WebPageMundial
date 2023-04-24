package Doggie.WebPage.Mundial.servicio;


import Doggie.WebPage.Mundial.dto.PosibleBalonOro;
import Doggie.WebPage.Mundial.dto.PosibleBotaOro;
import Doggie.WebPage.Mundial.dto.mapper.PosibleBalonOroMapper;
import Doggie.WebPage.Mundial.dto.mapper.PosibleBotaOroMapper;
import Doggie.WebPage.Mundial.modelo.repositorio.RepositorioJugador;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicioPremios {

    private final RepositorioJugador repositorioJugador;
    private final PosibleBotaOroMapper posibleBotaOroMapper;
    private final PosibleBalonOroMapper posibleBalonOroMapper;

    public PosibleBotaOro posibleBotaOro() {
        var jugador = repositorioJugador.findJugadorConMasGoles();
        return posibleBotaOroMapper.from(jugador);
    }

    public PosibleBalonOro posibleBalonOro() {
        var jugador = repositorioJugador.findJugadorConMasMVP();
        return posibleBalonOroMapper.from(jugador);
    }
}
