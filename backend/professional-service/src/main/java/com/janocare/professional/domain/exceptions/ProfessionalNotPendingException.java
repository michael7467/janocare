package com.janocare.professional.domain.exceptions;

public class ProfessionalNotPendingException extends RuntimeException {
    public ProfessionalNotPendingException(String message) {
        super(message);
    }
}