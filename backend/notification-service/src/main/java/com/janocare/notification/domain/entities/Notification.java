package com.janocare.notification.domain.entities;

import java.time.Instant;
import java.util.UUID;

import com.janocare.notification.domain.enums.NotificationStatus;
import com.janocare.notification.domain.enums.NotificationType;
import com.janocare.notification.domain.enums.NotificationChannel;

public class Notification {

    private UUID id;

    private UUID userId;

    private String subject;

    private String message;

    private String destination;

    private NotificationStatus status;

    private NotificationType type;

    private NotificationChannel channel;

    private UUID notificationCategoryId;

    private Instant createdAt;

    private Instant updatedAt;

    private Notification() {}

    public static Notification create(
            UUID userId,
            String subject,
            String message,
            String destination,
            NotificationType type,
            NotificationChannel channel
    ) {

        Notification notification =
                new Notification();

        notification.id = UUID.randomUUID();
        notification.userId = userId;
        notification.subject = subject;
        notification.message = message;
        notification.destination = destination;
        notification.status = NotificationStatus.PENDING;
        notification.type = type;
        notification.channel = channel;
        notification.createdAt = Instant.now();
        notification.updatedAt = Instant.now();

        return notification;
    }

    public static Notification restore(
            UUID id,
            UUID userId,
            String subject,
            String message,
            String destination,
            NotificationStatus status,
            NotificationType type,
            NotificationChannel channel,
            UUID notificationCategoryId,
            Instant createdAt,
            Instant updatedAt
    ) {

        Notification notification =
                new Notification();

        notification.id = id;
        notification.userId = userId;
        notification.subject = subject;
        notification.message = message;
        notification.destination = destination;
        notification.status = status;
        notification.type = type;
        notification.channel = channel;
        notification.notificationCategoryId =
                notificationCategoryId;
        notification.createdAt = createdAt;
        notification.updatedAt = updatedAt;

        return notification;
    }

    // ---------------------------------------------------------
    // BUSINESS METHODS
    // ---------------------------------------------------------

    public void markSent() {
        this.status = NotificationStatus.SENT;
        this.updatedAt = Instant.now();
    }

    public void markFailed() {
        this.status = NotificationStatus.FAILED;
        this.updatedAt = Instant.now();
    }

    // ---------------------------------------------------------
    // FACTORIES
    // ---------------------------------------------------------

    public static Notification bookingConfirmation(
            UUID userId,
            String email,
            String professionalName
    ) {
        return create(
                userId,
                "Booking Confirmation",
                "Your appointment booking with "
                        + professionalName
                        + " has been confirmed.",
                email,
                NotificationType.BOOKING_CONFIRMATION,
                NotificationChannel.EMAIL
        );
    }

    public static Notification paymentConfirmation(
            UUID userId,
            String email,
            String referenceNumber
    ) {
        return create(
                userId,
                "Payment Confirmation",
                "Your payment was successful. Reference: "
                        + referenceNumber,
                email,
                NotificationType.PAYMENT_CONFIRMATION,
                NotificationChannel.EMAIL
        );
    }

    public static Notification bookingCancellation(
            UUID userId,
            String email
    ) {
        return create(
                userId,
                "Booking Cancelled",
                "Your appointment booking has been cancelled.",
                email,
                NotificationType.BOOKING_CANCELLATION,
                NotificationChannel.EMAIL
        );
    }

    public static Notification paymentRefund(
            UUID userId,
            String email
    ) {
        return create(
                userId,
                "Payment Refunded",
                "Your payment refund has been processed.",
                email,
                NotificationType.PAYMENT_REFUND,
                NotificationChannel.EMAIL
        );
    }

    // ---------------------------------------------------------
    // GETTERS
    // ---------------------------------------------------------

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

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

    public UUID getNotificationCategoryId() {
        return notificationCategoryId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}