package com.janocare.professional.domain.exceptions;

public class ProfessionalAlreadyApprovedException extends RuntimeException {
    public ProfessionalAlreadyApprovedException(String message) {
        super(message);
    }
}