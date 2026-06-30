package com.janocare.notification.infrastructure.persistence.mappers;

import com.janocare.notification.domain.entities.Notification;

import com.janocare.notification.infrastructure.persistence.entities.NotificationEntity;
import com.janocare.notification.infrastructure.persistence.entities.NotificationCategoryEntity;

public class NotificationMapper {

    private NotificationMapper() {}

    public static NotificationEntity toEntity(
            Notification domain
    ) {

        NotificationEntity entity =
                new NotificationEntity();

        entity.setId(domain.getId());

        entity.setUserId(domain.getUserId());

        entity.setSubject(domain.getSubject());

        entity.setMessage(domain.getMessage());

        entity.setDestination(domain.getDestination());

        entity.setStatus(domain.getStatus());

        entity.setType(domain.getType());

        entity.setChannel(domain.getChannel());

        entity.setCreatedAt(domain.getCreatedAt());

        entity.setUpdatedAt(domain.getUpdatedAt());

        if (domain.getNotificationCategoryId() != null) {

            NotificationCategoryEntity category =
                    new NotificationCategoryEntity();

            category.setId(
                    domain.getNotificationCategoryId()
            );

            entity.setNotificationCategory(category);
        }

        return entity;
    }

    public static Notification toDomain(
            NotificationEntity entity
    ) {

        return Notification.restore(
                entity.getId(),
                entity.getUserId(),
                entity.getSubject(),
                entity.getMessage(),
                entity.getDestination(),
                entity.getStatus(),
                entity.getType(),
                entity.getChannel(),
                entity.getNotificationCategory() != null
                        ? entity.getNotificationCategory().getId()
                        : null,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}