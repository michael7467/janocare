package com.janocare.booking.domain.exceptions;

public class InvalidBookingOperationException extends RuntimeException {
    public InvalidBookingOperationException(String message) {
        super(message);
    }
}