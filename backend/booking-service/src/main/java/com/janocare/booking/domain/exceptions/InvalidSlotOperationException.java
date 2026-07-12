package com.janocare.booking.domain.exceptions;

public class InvalidSlotOperationException extends RuntimeException {
    public InvalidSlotOperationException(String message) {
        super(message);
    }
}