package Doggie.WebPage.Mundial.excepciones;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Getter
public abstract class RecursoException extends RuntimeException {

    /**
     * Nombre del recurso que no se ha encontrado.
     */
    private final String recurso;
    /**
     * Motivo por el cual no se ha encontrado el recurso.
     */
    private final String motivo;
    /**
     * Operación que se estaba intentando realizar cuando se ha producido la excepción.
     */
    private final String operacion;
    /**
     * Fecha y hora en que se ha producido la excepción.
     */
    private final String fechaOperacion;

    /**
     * Constructor de la excepción.
     *
     * @param recurso   Nombre del recurso que no se ha encontrado.
     * @param motivo    Motivo por el cual no se ha encontrado el recurso.
     * @param operacion Operación que se estaba intentando realizar cuando se ha producido la excepción.
     */

    protected RecursoException(String recurso, String motivo, String operacion) {
        super(buildErrorMessage(recurso, motivo, operacion));
        this.recurso = recurso;
        this.motivo = motivo;
        this.operacion = operacion;
        var fecha = LocalDateTime.now();
        var formateador = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy 'a las' HH 'horas con' mm 'minutos y' ss 'segundos.'");
        this.fechaOperacion = fecha.format(formateador);
    }

    /**
     * Construye un mensaje de error genérico para excepciones de recursos no encontrados.
     *
     * @param recurso   el nombre del recurso no encontrado.
     * @param motivo    el motivo por el que el recurso no se encontró.
     * @param operacion la operación que se intentó realizar en el recurso.
     * @return un mensaje de error que indica que no se pudo completar la operación en el recurso debido a una razón específica.
     */

    private static String buildErrorMessage(String recurso, String motivo, String operacion) {
        return String.format("No se pudo completar la operación '%s' en el recurso '%s' debido a '%s'.", operacion, recurso, motivo);
    }

    /**
     * Retorna el código de estado HTTP que debe ser devuelto como respuesta
     * a la petición que ha generado la excepción.
     *
     * @return El código de estado HTTP correspondiente.
     */

    protected abstract HttpStatus getHttpStatus();

    /**
     * Método que construye el mensaje de error que se mostrará al usuario.
     * Debe ser implementado por cada excepción concreta que extienda de esta clase.
     * <p>
     * Este método debe devolver un mensaje que incluya:
     * el recurso buscado o el id de este, el motivo y la fecha en la que se realizó la operación
     *
     * @return Mensaje de error personalizado para la excepción concreta.
     */

    protected String getMensaje(String formato) {
        return String.format(formato, recurso, motivo, fechaOperacion);
    }

    protected String getMensaje(String formato, Long id) {
        return String.format(formato, id, motivo, fechaOperacion);
    }

    protected String getMensaje(String formato, Long id, Long otroId) {
        return String.format(formato, id, otroId, motivo, fechaOperacion);
    }

    protected String getMensajeConId(String formato, Long id) {
        return String.format(formato, recurso, id, motivo, fechaOperacion);
    }
}
