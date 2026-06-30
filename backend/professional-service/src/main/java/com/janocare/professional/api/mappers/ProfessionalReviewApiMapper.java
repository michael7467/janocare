package com.janocare.professional.api.mappers;

import com.janocare.professional.api.responses.professionalreview.ProfessionalReviewResponse;
import com.janocare.professional.domain.entities.ProfessionalReview;

public class ProfessionalReviewApiMapper {

    private ProfessionalReviewApiMapper() {}

    public static ProfessionalReviewResponse toResponse(
            ProfessionalReview review
    ) {

        if (review == null) {
            return null;
        }

        ProfessionalReviewResponse r =
                new ProfessionalReviewResponse();

        r.id = review.getId();

        r.professionalId = review.getProfessionalId();

        r.userId = review.getUserId();

        r.appointmentBookingId =
                review.getAppointmentBookingId();

        r.reviewAnonymous =
                review.isReviewAnonymous();

        r.waitTimeRating =
                review.getWaitTimeRating();

        r.mannerRating =
                review.getMannerRating();

        r.review = review.getReview();

        r.overallRating =
                review.getOverallRating();

        r.doctorRecommended =
                review.isDoctorRecommended();

        return r;
    }
}