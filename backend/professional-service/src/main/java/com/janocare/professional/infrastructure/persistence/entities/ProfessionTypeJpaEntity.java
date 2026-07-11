package com.janocare.professional.infrastructure.persistence.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "profession_types",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_profession_type_name",
                        columnNames = "name"
                )
        }
)
public class ProfessionTypeJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    public UUID id;

    @Column(nullable = false, unique = true)
    public String name;

    @Column(length = 500)
    public String description;

    /**
     * Slot interval in minutes.
     * Knowledge-level configuration — Accountability pattern.
     * Governs BookingSlot generation for all professionals
     * of this type at the operational level.
     */
    @Column(name = "slot_interval", nullable = false)
    public Integer slotInterval;

    /**
     * Active flag — admin can deactivate without deleting.
     * Deactivated types are not shown to patients or professionals.
     */
    @Column(nullable = false)
    public boolean active = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    // ── relationship ─────────────────────────────────────────
    // Unidirectional from Professional → ProfessionType
    // This @OneToMany is the inverse side — LAZY, no cascade
    // Do not use this collection to navigate — query directly
    @OneToMany(
            mappedBy = "professionType",
            fetch = FetchType.LAZY
    )
    public List<ProfessionalJpaEntity> professionals = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (updatedAt == null) updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}