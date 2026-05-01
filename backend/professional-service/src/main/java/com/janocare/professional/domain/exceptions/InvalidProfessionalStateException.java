package domain.exceptions;

public class InvalidProfessionalStateException extends RuntimeException {
    public InvalidProfessionalStateException(String message) {
        super(message);
    }
}
