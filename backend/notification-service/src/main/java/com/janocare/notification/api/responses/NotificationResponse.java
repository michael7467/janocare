package com.janocare.notification.api.responses;

import java.util.UUID;

import com.janocare.notification.infrastructure.persistence.enums.NotificationStatus;
import com.janocare.notification.infrastructure.persistence.enums.NotificationType;
import com.janocare.notification.infrastructure.persistence.enums.NotificationChannel;

public class NotificationResponse {

    public UUID id;

    public UUID userId;

    public String subject;

    public String message;

    public String destination;

    public NotificationStatus status;

    public NotificationType type;

    public NotificationChannel channel;

    public UUID notificationCategoryId;
}