package com.janocare.notification.application.ports;

import com.janocare.notification.domain.entities.Notification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationPort {

    Notification save(Notification notification);

    Optional<Notification> findNotificationById(UUID id);

    List<Notification> findAllNotifications();

    List<Notification> findNotificationsByUserId(UUID userId);
}