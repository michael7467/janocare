package com.janocare.professional.domain.exceptions;

public class ProfessionTypeNotFoundException extends RuntimeException {

    public ProfessionTypeNotFoundException() {
        super("Profession type not found");
    }

    public ProfessionTypeNotFoundException(String message) {
        super(message);
    }
}