package com.janocare.notification.infrastructure.persistence.mappers;

import com.janocare.notification.domain.entities.NotificationCategory;

import com.janocare.notification.infrastructure.persistence.entities.NotificationCategoryEntity;

public class NotificationCategoryMapper {

    private NotificationCategoryMapper() {}

    public static NotificationCategoryEntity toEntity(
            NotificationCategory domain
    ) {

        NotificationCategoryEntity entity =
                new NotificationCategoryEntity();

        entity.setId(domain.getId());

        entity.setName(domain.getName());

        entity.setDescription(domain.getDescription());

        entity.setCreatedAt(domain.getCreatedAt());

        entity.setUpdatedAt(domain.getUpdatedAt());

        return entity;
    }

    public static NotificationCategory toDomain(
            NotificationCategoryEntity entity
    ) {

        return NotificationCategory.restore(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}