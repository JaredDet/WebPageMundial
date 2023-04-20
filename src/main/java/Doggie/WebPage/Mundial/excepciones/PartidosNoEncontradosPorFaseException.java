package Doggie.WebPage.Mundial.excepciones;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PartidosNoEncontradosPorFaseException extends RecursoException {

    /**
     * Identificador de la fase que no fue encontrada.
     */

    private final Long faseId;

    /**
     * Constructor de la excepción.
     *
     * @param faseId Identificador e la fase de la que no se encontraron partidos.
     */

    public PartidosNoEncontradosPorFaseException(Long faseId) {
        super("Partidos", "No existen partidos en el caché o en la base de datos para esta fase", "Get partidos");
        this.faseId = faseId;
    }

    public String getMensajePersonalizado() {
        var formato = "Lo sentimos, no se encontraron partidos para la fase con el ID '%d'.%n" +
                "Motivo: %s.%n" +
                "La operación se realizó el %s.%n" +
                "Por favor, verifique que ha proporcionado el ID correcto y vuelva a intentarlo.";
        return getMensaje(formato, faseId, false);
    }

    @Override
    protected HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
