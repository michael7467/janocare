package com.janocare.professional.application.queries.specialization;

import java.util.UUID;

public class FindSpecializationByIdQuery {

    public UUID specializationId;

    public FindSpecializationByIdQuery() {}

    public FindSpecializationByIdQuery(
            UUID specializationId
    ) {
        this.specializationId = specializationId;
    }
}