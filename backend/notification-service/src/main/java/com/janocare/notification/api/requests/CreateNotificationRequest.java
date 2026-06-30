package com.janocare.notification.api.requests;

import java.util.UUID;

import com.janocare.notification.infrastructure.persistence.enums.NotificationType;
import com.janocare.notification.infrastructure.persistence.enums.NotificationChannel;

public class CreateNotificationRequest {

    public UUID userId;

    public String subject;

    public String message;

    public String destination;

    public NotificationType type;

    public NotificationChannel channel;

    public UUID notificationCategoryId;
}