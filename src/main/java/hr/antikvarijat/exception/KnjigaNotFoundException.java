package hr.antikvarijat.exception;

public class KnjigaNotFoundException extends RuntimeException {
    public KnjigaNotFoundException(String message) {
        super(message);
    }
}
