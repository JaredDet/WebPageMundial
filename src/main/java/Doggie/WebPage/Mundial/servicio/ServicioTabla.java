package Doggie.WebPage.Mundial.servicio;

import Doggie.WebPage.Mundial.dto.ResumenPartido;
import Doggie.WebPage.Mundial.dto.Tabla;
import Doggie.WebPage.Mundial.dto.mapper.RondaMapper;
import Doggie.WebPage.Mundial.dto.mapper.TablaMapper;
import Doggie.WebPage.Mundial.modelo.repositorio.RepositorioGrupo;
import Doggie.WebPage.Mundial.servicio.cache.TablaCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicioTabla {
    private final TablaMapper tablaMapper;
    private final TablaCache tablaCache;
    private final RepositorioGrupo repositorioGrupo;
    private final RondaMapper rondaMapper;

    public Tabla findByNombreGrupo(String nombre) {
        var grupo = repositorioGrupo.findByNombre(nombre);
        var partidos = tablaCache.getPartidosByGrupoId(grupo.getGrupoId());
        return tablaMapper.from(partidos, grupo);
    }

    public List<Tabla> findAll() {
        return repositorioGrupo.findAll().stream()
                .map(grupo -> {
                    var partidos = tablaCache.getPartidosByGrupoId(grupo.getGrupoId());
                    return tablaMapper.from(partidos, grupo);
                }).toList();
    }

    public List<ResumenPartido> findFaseGrupos() {
        var partidos = tablaCache.getFaseGrupos();
        return rondaMapper.from(partidos);
    }
}

