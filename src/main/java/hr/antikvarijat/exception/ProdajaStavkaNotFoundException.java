package hr.antikvarijat.exception;

public class ProdajaStavkaNotFoundException extends RuntimeException {
    public ProdajaStavkaNotFoundException(String message) {
        super(message);
    }
}
