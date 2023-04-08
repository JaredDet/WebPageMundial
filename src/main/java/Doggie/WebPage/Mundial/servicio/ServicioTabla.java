package Doggie.WebPage.Mundial.servicio;

import Doggie.WebPage.Mundial.dto.Tabla;
import Doggie.WebPage.Mundial.dto.mapper.TablaMapper;
import Doggie.WebPage.Mundial.modelo.repositorio.RepositorioGrupo;
import Doggie.WebPage.Mundial.modelo.repositorio.RepositorioPartido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicioTabla {
    private final TablaMapper tablaMapper;
    private final RepositorioPartido repositorioPartido;
    private final RepositorioGrupo repositorioGrupo;

    public Tabla findByNombreGrupo(String nombre) {
        var grupo = repositorioGrupo.findByNombre(nombre);
        var partidos = repositorioPartido.findByGrupoId(grupo.getGrupoId());
        return tablaMapper.from(partidos, grupo);
    }

    public List<Tabla> findAll() {
        return repositorioGrupo.findAll().stream()
                .map(grupo -> tablaMapper
                        .from(repositorioPartido
                                .findByGrupoId(grupo.getGrupoId()), grupo))
                .toList();
    }
}