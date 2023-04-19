package Doggie.WebPage.Mundial.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class JugadoresNoEncontradosException extends RecursoException {

    /**
     * Identificador del partido en el que jugaron los jugadores que no fueron encontrados.
     */

    private final Long partidoId;

    /**
     * Identificador del equipo de los jugadores que no fueron encontrados.
     */

    private final Long equipoId;

    /**
     * Constructor de la excepción.
     *
     * @param partidoId Identificador del partido del que no se encontraron jugadores.
     * @param equipoId  Identificador del equipo al que pertenecen los jugadores no encontrados.
     */

    public JugadoresNoEncontradosException(Long partidoId, Long equipoId) {
        super("Jugadores", "No existen jugadores que hayan jugado al partido o que pertenezcan al equipo ni en el caché ni en la base de datos", "Get partido");
        this.partidoId = partidoId;
        this.equipoId = equipoId;
    }

    public String getMensajePersonalizado() {
        var formato = "Lo sentimos, no se encontraron jugadores que hayan participado en el partido con ID '%d' o que pertenezcan al equipo con ID '%d'. %n" +
                "Motivo: %s. %n" +
                "La operación se realizó el %s. %n" +
                "Por favor, verifica que hayas proporcionado los IDs correctos e intenta nuevamente.";
        return getMensaje(formato, partidoId, equipoId);
    }

    @Override
    protected HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
