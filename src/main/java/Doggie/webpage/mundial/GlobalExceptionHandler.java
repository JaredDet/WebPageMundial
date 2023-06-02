package Doggie.WebPage.Mundial;

import Doggie.WebPage.Mundial.excepciones.RecursoException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /*@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(IllegalArgumentException ex) {
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecursoException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(RecursoException ex) {
        ErrorResponse response = new ErrorResponse(ex.getHttpStatus(), ex.getMensajePersonalizado());
        var lineasMensaje = ex.getMensajePersonalizado().split("\\r?\\n");
        Arrays.stream(lineasMensaje).forEach(log::error);
        return new ResponseEntity<>(response, ex.getHttpStatus());
    }


    @Data
    public static class ErrorResponse {
        private final String status;
        private final String message;

        public ErrorResponse(HttpStatus status, String message) {
            this.status = status.toString();
            this.message = message;
        }
    }
}
