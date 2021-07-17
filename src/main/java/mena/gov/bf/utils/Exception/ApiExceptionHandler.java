package mena.gov.bf.utils.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

/**
 * ResponseStatusException personalisé pour afficher les message backend.
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> responseEntity(Exception ex) {
        ApiException apiException = null;
        if (ex instanceof ResponseStatusException) {
            final ResponseStatusException responseStatusException = (ResponseStatusException) ex;
            apiException = new ApiException(
                    responseStatusException.getStatus(), responseStatusException.getReason(), ex);
        } else {
            apiException = new ApiException(
                    HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
        ex.printStackTrace();

        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }
}
