package Doggie.WebPage.Mundial.controlador;

import Doggie.WebPage.Mundial.dto.*;
import Doggie.WebPage.Mundial.servicio.ServicioPartido;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partidos")
@RequiredArgsConstructor
public class ControladorPartido {

    private final ServicioPartido servicioPartido;

    /**
     * Recupera los datos de un partido a través del identificador del partido.
     *
     * @param partidoId el identificador del partido.
     * @return los datos del partido en formato JSON.
     */

    @GetMapping("{partidoId}")
    public DatosPartido partido(@PathVariable Long partidoId) {
        return servicioPartido.datosPartido(partidoId);
    }

    /**
     * Obtiene una lista de las plantillas utilizadas en un partido.
     *
     * @param partidoId el identificador del partido del que se quieren obtener las plantillas.
     * @return una lista de las plantillas utilizadas en el partido.
     */

    @GetMapping("/{partidoId}/plantillas")
    public List<Plantilla> plantillas(@PathVariable Long partidoId) {
        return servicioPartido.findPlantillasByPartido(partidoId);
    }

    /**
     * Devuelve una lista de objetos GolesEquipo que contienen la información de los goles
     * anotados por cada equipo en el partido con el id especificado.
     *
     * @param partidoId el id del partido del que se quieren obtener los goles.
     * @return una lista de objetos GolesEquipo que contienen la información de los goles
     * anotados por cada equipo en el partido.
     */

    @GetMapping("/{partidoId}/goles")
    public List<GolesEquipo> goles(@PathVariable Long partidoId) {
        return servicioPartido.findGolesByPartido(partidoId);
    }

    /**
     * Devuelve una lista con los marcadores de cada equipo en el partido especificado por su ID.
     *
     * @param partidoId el ID del partido del cual se desea obtener el marcador.
     * @return una lista de objetos MarcadorEquipo que contienen la información del marcador de cada equipo.
     */

    @GetMapping("/{partidoId}/marcador")
    public List<MarcadorEquipo> marcador(@PathVariable Long partidoId) {
        return servicioPartido.findMarcadorByPartido(partidoId);
    }


    /**
     * Devuelve una lista con estadísticas del partido con el id especificado.
     * Las estadísticas incluyen el tiros, tiros al arco, faltas, entre otros.
     *
     * @param partidoId el id del partido del que se quieren obtener las estadísticas.
     * @return una lista de estadísticas del partido con el id especificado.
     */

    @GetMapping("/{partidoId}/estadisticas")
    public List<DatosEstadistica> estadisticas(@PathVariable Long partidoId) {
        return servicioPartido.findDatosEstadisticasByPartido(partidoId);
    }

    /**
     * Devuelve una lista con los resultados de los penales de cada equipo en un partido.
     *
     * @param partidoId el identificador del partido del que se quieren obtener los resultados de los penales.
     * @return una lista de objetos Penales, que contienen la información de los resultados de los penales.
     */

    @GetMapping("/{partidoId}/penales")
    public List<Penales> penales(@PathVariable Long partidoId) {
        return servicioPartido.findPenalesByPartido(partidoId);
    }

    /**
     * Devuelve una lista con el marcador de penales de los equipos del partido con el id especificado.
     *
     * @param partidoId el id del partido del cual se desea obtener el marcador de los penales.
     * @return una lista de objetos MarcadorEquipo que representan el marcador de los penales del partido.
     */

    @GetMapping("/{partidoId}/marcador/penales")
    public List<MarcadorEquipo> marcadorPenales(@PathVariable Long partidoId) {
        return servicioPartido.findMarcadorPenalesByPartido(partidoId);
    }
}
