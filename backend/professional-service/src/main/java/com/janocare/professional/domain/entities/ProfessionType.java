package com.janocare.professional.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class ProfessionType {

    private UUID id;

    private String name;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    protected ProfessionType() {}

    // =====================================================
    // CREATE
    // =====================================================

    public static ProfessionType create(
            String name,
            String description
    ) {

        ProfessionType professionType =
                new ProfessionType();

        professionType.id = UUID.randomUUID();

        professionType.name = name;

        professionType.description = description;

        professionType.createdAt =
                LocalDateTime.now();

        return professionType;
    }

    // =====================================================
    // RESTORE
    // =====================================================

    public static ProfessionType restore(
            UUID id,
            String name,
            String description,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {

        ProfessionType professionType =
                new ProfessionType();

        professionType.id = id;

        professionType.name = name;

        professionType.description = description;

        professionType.createdAt = createdAt;

        professionType.updatedAt = updatedAt;

        return professionType;
    }

    // =====================================================
    // BUSINESS METHODS
    // =====================================================

    public void update(
            String name,
            String description
    ) {

        this.name = name;

        this.description = description;

        this.updatedAt = LocalDateTime.now();
    }

    // =====================================================
    // GETTERS
    // =====================================================

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}