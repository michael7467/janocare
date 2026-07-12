package com.janocare.booking.domain.entities;

import java.time.Instant;
import java.util.UUID;

public class CancellationReason {

    private UUID id;
    private String reason;
    private Instant createdAt;
    private Instant updatedAt;

    private CancellationReason() {}

    // =====================================================
    // FACTORY — create new
    // =====================================================

    public static CancellationReason create(String reason) {

        if (reason == null || reason.isBlank())
            throw new IllegalArgumentException(
                    "Cancellation reason is required");

        CancellationReason cr = new CancellationReason();
        cr.id        = UUID.randomUUID();
        cr.reason    = reason.trim();
        cr.createdAt = Instant.now();
        cr.updatedAt = Instant.now();
        return cr;
    }

    // =====================================================
    // FACTORY — restore from persistence
    // =====================================================

    public static CancellationReason restore(
            UUID id,
            String reason,
            Instant createdAt,
            Instant updatedAt
    ) {
        CancellationReason cr = new CancellationReason();
        cr.id        = id;
        cr.reason    = reason;
        cr.createdAt = createdAt;
        cr.updatedAt = updatedAt;
        return cr;
    }

    // =====================================================
    // BUSINESS METHODS
    // =====================================================

    public void update(String reason) {
        if (reason == null || reason.isBlank())
            throw new IllegalArgumentException(
                    "Cancellation reason cannot be blank");
        this.reason    = reason.trim();
        this.updatedAt = Instant.now();
    }

    // =====================================================
    // GETTERS
    // =====================================================

    public UUID getId()          { return id; }
    public String getReason()    { return reason; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}