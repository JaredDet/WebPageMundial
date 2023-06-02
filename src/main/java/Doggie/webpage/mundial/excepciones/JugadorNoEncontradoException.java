package Doggie.WebPage.Mundial.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class JugadorNoEncontradoException extends RecursoException {

    private final String nombre;

    public JugadorNoEncontradoException(String nombre) {
        super("Jugador", "No existe un jugador de nombre " + nombre + " en la base de datos", "Get jugador");
        this.nombre = nombre;
    }

    public JugadorNoEncontradoException(String nombre, Long partidoId) {
        super("Jugador", "No existe un jugador de nombre " + nombre + " en el partido con Id " + partidoId, "Get jugador en partido");
        this.nombre = nombre;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public String getMensajePersonalizado() {
        var formato = "Lo sentimos, no se pudo cargar el recurso %s con nombre %s%n" +
                "debido a que %s.%n" +
                "La operación se realizó el %s.%n" +
                "Por favor, verifique que la lista de participantes esté cargada correctamente.";
        return getMensaje(formato, nombre);
    }
}
