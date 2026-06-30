package com.janocare.professional.domain.entities;

import java.util.UUID;

public class Specialization {

    private UUID id;
    private String name;
    private String description;

    protected Specialization() {}

    public static Specialization create(
            String name,
            String description
    ) {
        Specialization specialization = new Specialization();
        specialization.id = UUID.randomUUID();
        specialization.name = name;
        specialization.description = description;
        return specialization;
    }

    public static Specialization restore(
            UUID id,
            String name,
            String description
    ) {
        Specialization specialization = new Specialization();
        specialization.id = id;
        specialization.name = name;
        specialization.description = description;
        return specialization;
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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}