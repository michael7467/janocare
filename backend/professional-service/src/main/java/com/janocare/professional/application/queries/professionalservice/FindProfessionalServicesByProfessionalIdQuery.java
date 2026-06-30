package com.janocare.professional.application.queries.professionalservice;

import java.util.UUID;

public class FindProfessionalServicesByProfessionalIdQuery {
    public UUID professionalId;

    public FindProfessionalServicesByProfessionalIdQuery() {}

    public FindProfessionalServicesByProfessionalIdQuery(UUID professionalId) {
        this.professionalId = professionalId;
    }
}