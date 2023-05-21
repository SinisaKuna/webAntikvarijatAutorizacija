package hr.antikvarijat.exception;

public class IzdavacNotFoundException extends RuntimeException {
    public IzdavacNotFoundException(String message) {
        super(message);
    }
}
