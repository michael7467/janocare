package com.janocare.professional.application.queries.professional;

import java.util.UUID;

public class FindProfessionalReviewsQuery {

    public UUID professionalId;

    public FindProfessionalReviewsQuery() {}

    public FindProfessionalReviewsQuery(UUID professionalId) {
        this.professionalId = professionalId;
    }
}