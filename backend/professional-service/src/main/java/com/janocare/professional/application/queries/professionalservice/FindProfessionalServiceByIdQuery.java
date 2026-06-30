package com.janocare.professional.application.queries.professionalservice;

import java.util.UUID;

public class FindProfessionalServiceByIdQuery {
    public UUID serviceId;

    public FindProfessionalServiceByIdQuery() {}

    public FindProfessionalServiceByIdQuery(UUID serviceId) {
        this.serviceId = serviceId;
    }
}