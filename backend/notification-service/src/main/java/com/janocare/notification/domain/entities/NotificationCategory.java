package com.janocare.notification.domain.entities;

import java.time.Instant;
import java.util.UUID;

public class NotificationCategory {

    private UUID id;

    private String name;

    private String description;

    private Instant createdAt;

    private Instant updatedAt;

    private NotificationCategory() {}

    public static NotificationCategory create(
            String name,
            String description
    ) {

        NotificationCategory category =
                new NotificationCategory();

        category.id = UUID.randomUUID();
        category.name = name;
        category.description = description;
        category.createdAt = Instant.now();
        category.updatedAt = Instant.now();

        return category;
    }

    public static NotificationCategory restore(
            UUID id,
            String name,
            String description,
            Instant createdAt,
            Instant updatedAt
    ) {

        NotificationCategory category =
                new NotificationCategory();

        category.id = id;
        category.name = name;
        category.description = description;
        category.createdAt = createdAt;
        category.updatedAt = updatedAt;

        return category;
    }

    // ---------------------------------------------------------
    // GETTERS
    // ---------------------------------------------------------

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}