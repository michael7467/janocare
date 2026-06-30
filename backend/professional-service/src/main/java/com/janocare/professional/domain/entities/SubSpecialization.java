package com.janocare.professional.domain.entities;

import java.util.UUID;

public class SubSpecialization {

    private UUID id;
    private UUID specializationId;
    private String name;
    private String description;

    protected SubSpecialization() {}

    public static SubSpecialization create(
            UUID specializationId,
            String name,
            String description
    ) {
        SubSpecialization subSpecialization = new SubSpecialization();
        subSpecialization.id = UUID.randomUUID();
        subSpecialization.specializationId = specializationId;
        subSpecialization.name = name;
        subSpecialization.description = description;
        return subSpecialization;
    }

    public static SubSpecialization restore(
            UUID id,
            UUID specializationId,
            String name,
            String description
    ) {
        SubSpecialization subSpecialization = new SubSpecialization();
        subSpecialization.id = id;
        subSpecialization.specializationId = specializationId;
        subSpecialization.name = name;
        subSpecialization.description = description;
        return subSpecialization;
    }

    public void update(
            String name,
            String description
    ) {
        this.name = name;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public UUID getSpecializationId() {
        return specializationId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}