package Doggie.WebPage.Mundial.servicio;

import Doggie.WebPage.Mundial.dto.EquipoPeticion;
import Doggie.WebPage.Mundial.dto.PartidoPeticion;
import Doggie.WebPage.Mundial.dto.mapper.PartidoMapper;
import Doggie.WebPage.Mundial.modelo.repositorio.RepositorioPartido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class ServicioRegistroPartido {

    private final RepositorioPartido repositorioPartido;
    private final PartidoMapper partidoMapper;

    public void registrar(PartidoPeticion partidoPeticion) {
        var partido = partidoMapper.from(partidoPeticion);
        repositorioPartido.save(partido);

        var local = partidoPeticion.equipoLocal();
        var visita = partidoPeticion.equipoVisita();

        log.info(String.format("Se ha registrado con éxito el partido %s vs %s en la base de datos",
                local.nombre(), visita.nombre()));
    }
}
