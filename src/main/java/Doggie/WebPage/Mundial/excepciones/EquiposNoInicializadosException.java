package Doggie.WebPage.Mundial.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class EquiposNoInicializadosException extends RecursoException {

    private final Long partidoId;

    public EquiposNoInicializadosException(Long partidoId) {
        super("Equipos participantes", "La lista de equipos participantes no ha sido inicializada", "Verificación equipos");
        this.partidoId = partidoId;
    }

    public String getMensajePersonalizado() {
        var formato = "Lo sentimos, no se pudo cargar el recurso %s para el partido con ID = %d%n" +
                "debido a que %s.%n" +
                "La operación se realizó el %s.%n" +
                "Por favor, verifique que la lista de participantes esté cargada correctamente.";
        return getMensaje(formato, partidoId, true);
    }

    @Override
    protected HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
