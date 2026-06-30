package com.janocare.booking.infrastructure.clients.notification;

public class NotificationApiResponse<T> {

    public boolean success;

    public String message;

    public T data;
}