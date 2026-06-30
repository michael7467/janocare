package com.janocare.booking.domain.entities;

import java.time.Instant;
import java.util.UUID;

public class CancellationReason {

    private UUID id;
    private String reason;
    private Instant createdAt;
    private Instant updatedAt;

    private CancellationReason() {}

    public static CancellationReason create(String reason) {
        CancellationReason cancellationReason =
                new CancellationReason();

        cancellationReason.id = UUID.randomUUID();
        cancellationReason.reason = reason;
        cancellationReason.createdAt = Instant.now();
        cancellationReason.updatedAt = Instant.now();

        return cancellationReason;
    }

    public static CancellationReason restore(
            UUID id,
            String reason,
            Instant createdAt,
            Instant updatedAt
    ) {
        CancellationReason cancellationReason =
                new CancellationReason();

        cancellationReason.id = id;
        cancellationReason.reason = reason;
        cancellationReason.createdAt = createdAt;
        cancellationReason.updatedAt = updatedAt;

        return cancellationReason;
    }

    public void update(String reason) {
        this.reason = reason;
        this.updatedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public String getReason() { return reason; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}