package com.janocare.professional.domain.entities;

import java.util.UUID;

public class ProfessionalService {

    private UUID id;

    private UUID professionalId;

    private String serviceName;

    protected ProfessionalService() {}

    public static ProfessionalService create(
            UUID professionalId,
            String serviceName
    ) {

        ProfessionalService service = new ProfessionalService();

        service.id = UUID.randomUUID();
        service.professionalId = professionalId;
        service.serviceName = serviceName;

        return service;
    }

    public static ProfessionalService restore(
            UUID id,
            UUID professionalId,
            String serviceName
    ) {

        ProfessionalService service = new ProfessionalService();

        service.id = id;
        service.professionalId = professionalId;
        service.serviceName = serviceName;

        return service;
    }
    public void update(String serviceName) {
        this.serviceName = serviceName;
    }
    public UUID getId() {
        return id;
    }

    public UUID getProfessionalId() {
        return professionalId;
    }

    public String getServiceName() {
        return serviceName;
    }
}