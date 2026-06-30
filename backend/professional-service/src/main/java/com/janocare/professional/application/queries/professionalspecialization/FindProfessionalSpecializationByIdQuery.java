package com.janocare.professional.application.queries.professionalspecialization;

import java.util.UUID;

public class FindProfessionalSpecializationByIdQuery {

    public UUID professionalSpecializationId;

    public FindProfessionalSpecializationByIdQuery() {}

    public FindProfessionalSpecializationByIdQuery(
            UUID professionalSpecializationId
    ) {
        this.professionalSpecializationId =
                professionalSpecializationId;
    }
}