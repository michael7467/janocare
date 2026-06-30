package com.janocare.professional.domain.entities;

import java.util.UUID;

public class ProfessionalSpecialization {

    private UUID id;

    private UUID professionalId;

    private UUID specializationId;

    protected ProfessionalSpecialization() {}

    public static ProfessionalSpecialization create(
            UUID professionalId,
            UUID specializationId
    ) {

        ProfessionalSpecialization specialization = new ProfessionalSpecialization();

        specialization.id = UUID.randomUUID();
        specialization.professionalId = professionalId;
        specialization.specializationId = specializationId;

        return specialization;
    }

    public static ProfessionalSpecialization restore(
            UUID id,
            UUID professionalId,
            UUID specializationId
    ) {

        ProfessionalSpecialization specialization = new ProfessionalSpecialization();

        specialization.id = id;
        specialization.professionalId = professionalId;
        specialization.specializationId = specializationId;

        return specialization;
    }
    public void update(UUID specializationId) {
        this.specializationId = specializationId;
    }
    public UUID getId() {
        return id;
    }

    public UUID getProfessionalId() {
        return professionalId;
    }

    public UUID getSpecializationId() {
        return specializationId;
    }
}