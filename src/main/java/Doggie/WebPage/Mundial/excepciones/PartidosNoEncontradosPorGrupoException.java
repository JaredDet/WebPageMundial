package Doggie.WebPage.Mundial.excepciones;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PartidosNoEncontradosPorGrupoException extends RecursoException {

    /**
     * Identificador del grupo que no fue encontrado.
     */

    private final Long grupoId;

    /**
     * Constructor de la excepción.
     *
     * @param grupoId Identificador del grupo del que no se encontraron partidos.
     */

    public PartidosNoEncontradosPorGrupoException(Long grupoId) {
        super("Partidos", "No existen partidos en el caché o en la base de datos para el grupo", "Get partidos");
        this.grupoId = grupoId;
    }

    public String getMensajePersonalizado() {
        var formato = "Lo sentimos, no se encontraron partidos cuyo grupo tuviera el ID '%d'.%n" +
                "Motivo: %s.%n" +
                "La operación se realizó el %s.%n" +
                "Por favor, verifique que ha proporcionado el ID correcto y vuelva a intentarlo.";
        return getMensaje(formato, grupoId);
    }

    @Override
    protected HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
