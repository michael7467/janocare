package com.janocare.notification.infrastructure.persistence.entities;

import jakarta.persistence.*;

import java.util.UUID;

import com.janocare.notification.infrastructure.persistence.enums.NotificationStatus;
import com.janocare.notification.infrastructure.persistence.enums.NotificationType;
import com.janocare.notification.infrastructure.persistence.enums.NotificationChannel;

@Entity
@Table(name = "notifications")
public class NotificationEntity extends BaseEntity {

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false)
    private String destination;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationChannel channel;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @ManyToOne
    @JoinColumn(name = "notification_category_id")
    private NotificationCategoryEntity notificationCategory;

    // -------------------------
    // Getters
    // -------------------------

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public String getDestination() {
        return destination;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public NotificationType getType() {
        return type;
    }

    public NotificationChannel getChannel() {
        return channel;
    }

    public UUID getUserId() {
        return userId;
    }

    public NotificationCategoryEntity getNotificationCategory() {
        return notificationCategory;
    }

    // -------------------------
    // Setters
    // -------------------------

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public void setChannel(NotificationChannel channel) {
        this.channel = channel;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setNotificationCategory(
            NotificationCategoryEntity notificationCategory
    ) {
        this.notificationCategory = notificationCategory;
    }
}