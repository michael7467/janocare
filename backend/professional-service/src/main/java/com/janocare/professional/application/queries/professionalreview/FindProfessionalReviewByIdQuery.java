package com.janocare.professional.application.queries.professionalreview;

import java.util.UUID;

public class FindProfessionalReviewByIdQuery {

    public UUID reviewId;

    public FindProfessionalReviewByIdQuery() {}

    public FindProfessionalReviewByIdQuery(UUID reviewId) {
        this.reviewId = reviewId;
    }
}