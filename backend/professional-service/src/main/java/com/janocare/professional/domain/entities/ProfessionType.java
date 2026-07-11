package com.janocare.professional.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class ProfessionType {

    private UUID id;

    private String name;

    private String description;

    /**
     * Slot interval in minutes — the knowledge-level configuration
     * that governs how BookingSlots are generated for all
     * professionals of this type.
     *
     * Accountability pattern (Fowler):
     * ProfessionType = knowledge level (rarely changes)
     * BookingSlot    = operational level (changes daily)
     *
     * Adding a new profession type with a different interval
     * requires only a data operation — zero code change.
     */
    private Integer slotInterval;

    private boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    protected ProfessionType() {}

    // =====================================================
    // FACTORY — create new
    // =====================================================

    public static ProfessionType create(
            String name,
            String description,
            Integer slotInterval
    ) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Profession type name is required");
        }
        if (slotInterval == null || slotInterval <= 0) {
            throw new IllegalArgumentException(
                    "Slot interval must be a positive number of minutes");
        }

        ProfessionType pt = new ProfessionType();
        pt.id           = UUID.randomUUID();
        pt.name         = name.trim();
        pt.description  = description;
        pt.slotInterval = slotInterval;
        pt.active       = true; // active by default when created
        pt.createdAt    = LocalDateTime.now();
        return pt;
    }

    // =====================================================
    // FACTORY — restore from persistence
    // =====================================================

    public static ProfessionType restore(
            UUID id,
            String name,
            String description,
            Integer slotInterval,
            boolean active,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        ProfessionType pt = new ProfessionType();
        pt.id           = id;
        pt.name         = name;
        pt.description  = description;
        pt.slotInterval = slotInterval;
        pt.active       = active;
        pt.createdAt    = createdAt;
        pt.updatedAt    = updatedAt;
        return pt;
    }

    // =====================================================
    // BUSINESS METHODS
    // =====================================================

    /**
     * Partial update — only updates non-null fields.
     * Admin manages the knowledge level.
     */
    public void update(
            String name,
            String description,
            Integer slotInterval,
            Boolean active
    ) {
        if (name != null && !name.isBlank()) {
            this.name = name.trim();
        }
        if (description != null) {
            this.description = description;
        }
        if (slotInterval != null) {
            if (slotInterval <= 0) {
                throw new IllegalArgumentException(
                        "Slot interval must be a positive number of minutes");
            }
            this.slotInterval = slotInterval;
        }
        if (active != null) {
            this.active = active;
        }
        this.updatedAt = LocalDateTime.now();
    }

    public void activate() {
        this.active    = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.active    = false;
        this.updatedAt = LocalDateTime.now();
    }

    // =====================================================
    // GETTERS
    // =====================================================

    public UUID getId()              { return id; }
    public String getName()          { return name; }
    public String getDescription()   { return description; }
    public Integer getSlotInterval() { return slotInterval; }
    public boolean isActive()        { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}