package com.janocare.professional.application.queries.subspecialization;

import java.util.UUID;

public class FindSubSpecializationsBySpecializationIdQuery {

    public UUID specializationId;

    public FindSubSpecializationsBySpecializationIdQuery() {}

    public FindSubSpecializationsBySpecializationIdQuery(
            UUID specializationId
    ) {
        this.specializationId =
                specializationId;
    }
}