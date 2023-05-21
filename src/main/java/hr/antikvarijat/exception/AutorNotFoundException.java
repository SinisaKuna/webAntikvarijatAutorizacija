package hr.antikvarijat.exception;

public class AutorNotFoundException extends RuntimeException {
    public AutorNotFoundException(String message) {
        super(message);
    }
}
