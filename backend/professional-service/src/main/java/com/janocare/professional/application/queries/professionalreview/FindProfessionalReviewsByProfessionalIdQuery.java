package com.janocare.professional.application.queries.professionalreview;

import java.util.UUID;

public class FindProfessionalReviewsByProfessionalIdQuery {

    public UUID professionalId;

    public FindProfessionalReviewsByProfessionalIdQuery() {}

    public FindProfessionalReviewsByProfessionalIdQuery(
            UUID professionalId
    ) {
        this.professionalId = professionalId;
    }
}