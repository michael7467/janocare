package com.janocare.notification.application.handlers;

import com.janocare.notification.application.commands.*;

import com.janocare.notification.application.ports.NotificationPort;

import com.janocare.notification.application.queries.*;

import com.janocare.notification.domain.entities.Notification;

import jakarta.enterprise.context.ApplicationScoped;

import jakarta.inject.Inject;

import jakarta.transaction.Transactional;

import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class NotificationHandler {

    @Inject
    NotificationPort notificationPort;

    @Transactional
    public Notification createNotification(
            CreateNotificationCommand command
    ) {

        Notification notification =
                Notification.create(
                        command.userId,
                        command.subject,
                        command.message,
                        command.destination,
                        command.type,
                        command.channel
                );

        return notificationPort.save(notification);
    }

    @Transactional
    public Notification markNotificationSent(
            MarkNotificationSentCommand command
    ) {

        Notification notification =
                notificationPort
                        .findNotificationById(
                                command.notificationId
                        )
                        .orElseThrow(() ->
                                new NotFoundException(
                                        "Notification not found"
                                )
                        );

        notification.markSent();

        return notificationPort.save(notification);
    }

    @Transactional
    public Notification markNotificationFailed(
            MarkNotificationFailedCommand command
    ) {

        Notification notification =
                notificationPort
                        .findNotificationById(
                                command.notificationId
                        )
                        .orElseThrow(() ->
                                new NotFoundException(
                                        "Notification not found"
                                )
                        );

        notification.markFailed();

        return notificationPort.save(notification);
    }

    public Notification findNotificationById(
            FindNotificationByIdQuery query
    ) {

        return notificationPort
                .findNotificationById(
                        query.notificationId
                )
                .orElseThrow(() ->
                        new NotFoundException(
                                "Notification not found"
                        )
                );
    }

    public List<Notification> findAllNotifications(
            FindAllNotificationsQuery query
    ) {

        if (query.userId != null) {

            return notificationPort
                    .findNotificationsByUserId(
                            query.userId
                    );
        }

        return notificationPort
                .findAllNotifications();
    }
}