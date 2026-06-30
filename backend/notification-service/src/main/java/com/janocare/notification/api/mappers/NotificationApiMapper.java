package com.janocare.notification.api.mappers;

import com.janocare.notification.api.responses.NotificationResponse;
import com.janocare.notification.domain.entities.Notification;

public class NotificationApiMapper {

    private NotificationApiMapper() {}

    public static NotificationResponse toResponse(
            Notification notification
    ) {
        if (notification == null) {
            return null;
        }

        NotificationResponse response =
                new NotificationResponse();

        response.id = notification.getId();
        response.userId = notification.getUserId();
        response.subject = notification.getSubject();
        response.message = notification.getMessage();
        response.destination = notification.getDestination();
        response.status = notification.getStatus();
        response.type = notification.getType();
        response.channel = notification.getChannel();
        response.notificationCategoryId =
                notification.getNotificationCategoryId();

        return response;
    }
}