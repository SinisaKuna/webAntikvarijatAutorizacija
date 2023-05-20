package hr.antikvarijat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DrzavaNotFoundException extends RuntimeException {

    public DrzavaNotFoundException(String message) {
        super(message);
    }
}
