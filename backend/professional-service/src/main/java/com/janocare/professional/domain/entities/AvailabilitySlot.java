package domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class AvailabilitySlot {

    private final UUID id;
    private final LocalDateTime start;
    private final LocalDateTime end;

    public AvailabilitySlot(UUID id, LocalDateTime start, LocalDateTime end) {
        if (end.isBefore(start))
            throw new IllegalArgumentException("End time cannot be before start time");

        this.id = id;
        this.start = start;
        this.end = end;
    }

    public UUID getId() { return id; }
    public LocalDateTime getStart() { return start; }
    public LocalDateTime getEnd() { return end; }
}
