package com.janocare.professional.application.queries.professionalspecialization;

import java.util.UUID;

public class FindProfessionalSpecializationsByProfessionalIdQuery {

    public UUID professionalId;

    public FindProfessionalSpecializationsByProfessionalIdQuery() {}

    public FindProfessionalSpecializationsByProfessionalIdQuery(
            UUID professionalId
    ) {
        this.professionalId = professionalId;
    }
}