package domain.exceptions;

public class SpecializationAlreadyExistsException extends RuntimeException {
    public SpecializationAlreadyExistsException(String message) {
        super(message);
    }
}
