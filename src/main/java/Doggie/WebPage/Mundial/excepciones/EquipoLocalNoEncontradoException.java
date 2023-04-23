package Doggie.WebPage.Mundial.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EquipoLocalNoEncontradoException extends RecursoException {


    public EquipoLocalNoEncontradoException() {
        super("Equipo", "Ninguno de los equipos es local.", "Buscar equipo local.");
    }

    public String getMensajePersonalizado() {
        var formato = "Lo sentimos, no se encontró ningún equipo local '%d'.%n" +
                "Motivo: %s.%n" +
                "Operación realizada el %s.%n" +
                "Por favor, verifique que los equipos tienen la variable esLocal bien asignada.";
        return getMensaje(formato);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
