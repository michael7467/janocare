package com.janocare.notification.application.queries;

import java.util.UUID;

public class FindNotificationByIdQuery {

    public UUID notificationId;

    public FindNotificationByIdQuery(
            UUID notificationId
    ) {
        this.notificationId = notificationId;
    }
}