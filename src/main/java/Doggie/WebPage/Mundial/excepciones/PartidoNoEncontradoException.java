package Doggie.WebPage.Mundial.excepciones;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción lanzada cuando se intenta acceder a un partido que no existe.
 * Extiende de la clase abstracta RecursoNoEncontradoException.
 */

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public final class PartidoNoEncontradoException extends RecursoException {

    /**
     * Identificador del partido que no fue encontrado.
     */

    private final Long partidoId;

    /**
     * Constructor de la excepción.
     *
     * @param partidoId Identificador del partido que no se pudo encontrar.
     */

    public PartidoNoEncontradoException(Long partidoId) {
        super("Partido", "No existe el partido ni en el caché ni en la base de datos", "Get partido");
        this.partidoId = partidoId;
    }

    public String getMensajePersonalizado() {
        var formato = "Lo sentimos, no se encontró el partido con el ID '%d'.%n" +
                "Motivo: %s.%n" +
                "Operación realizada el %s.%n" +
                "Por favor, verifique que ha proporcionado el ID correcto y vuelva a intentarlo.";
        return getMensaje(formato, partidoId, false);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
