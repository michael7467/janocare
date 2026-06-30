package com.janocare.professional.application.queries.professionalsubspecialization;

import java.util.UUID;

public class FindProfessionalSubSpecializationsByProfessionalIdQuery {

    public UUID professionalId;

    public FindProfessionalSubSpecializationsByProfessionalIdQuery() {}

    public FindProfessionalSubSpecializationsByProfessionalIdQuery(
            UUID professionalId
    ) {
        this.professionalId = professionalId;
    }
}