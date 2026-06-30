package com.janocare.notification.infrastructure.persistence.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "notification_categories")
public class NotificationCategoryEntity extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    // -------------------------
    // Getters
    // -------------------------

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    // -------------------------
    // Setters
    // -------------------------

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}