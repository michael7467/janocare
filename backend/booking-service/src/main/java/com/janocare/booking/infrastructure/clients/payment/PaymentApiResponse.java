package com.janocare.booking.infrastructure.clients.payment;

public class PaymentApiResponse<T> {

    public boolean success;

    public String message;

    public T data;
}