package com.janocare.professional.domain.exceptions;

public class ValidationException extends RuntimeException {

    public ValidationException() {
        super("Validation failed");
    }

    public ValidationException(String message) {
        super(message);
    }
}