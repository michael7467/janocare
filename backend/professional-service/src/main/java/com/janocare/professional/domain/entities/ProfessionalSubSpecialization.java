package com.janocare.professional.domain.entities;

import java.util.UUID;

public class ProfessionalSubSpecialization {

    private UUID id;
    private UUID professionalId;
    private UUID subSpecializationId;

    protected ProfessionalSubSpecialization() {}

    public static ProfessionalSubSpecialization create(UUID professionalId, UUID subSpecializationId) {
        ProfessionalSubSpecialization p = new ProfessionalSubSpecialization();
        p.id = UUID.randomUUID();
        p.professionalId = professionalId;
        p.subSpecializationId = subSpecializationId;
        return p;
    }

    public static ProfessionalSubSpecialization restore(UUID id, UUID professionalId, UUID subSpecializationId) {
        ProfessionalSubSpecialization p = new ProfessionalSubSpecialization();
        p.id = id;
        p.professionalId = professionalId;
        p.subSpecializationId = subSpecializationId;
        return p;
    }
    public void update(UUID subSpecializationId) {
        this.subSpecializationId = subSpecializationId;
    }
    public UUID getId() { return id; }
    public UUID getProfessionalId() { return professionalId; }
    public UUID getSubSpecializationId() { return subSpecializationId; }
}