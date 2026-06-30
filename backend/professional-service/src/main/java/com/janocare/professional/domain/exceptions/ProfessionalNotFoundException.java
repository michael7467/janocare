package com.janocare.professional.domain.exceptions;

public class ProfessionalNotFoundException extends RuntimeException {

    public ProfessionalNotFoundException() {
        super("Professional not found");
    }

    public ProfessionalNotFoundException(String message) {
        super(message);
    }
}