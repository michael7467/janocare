package com.janocare.professional.infrastructure.persistence.mappers;

import com.janocare.professional.domain.entities.ProfessionalReview;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalReviewJpaEntity;

public class ProfessionalReviewPersistenceMapper {

    private ProfessionalReviewPersistenceMapper() {}

    public static ProfessionalReviewJpaEntity toJpaEntity(
            ProfessionalReview domain,
            ProfessionalJpaEntity professional
    ) {

        ProfessionalReviewJpaEntity entity =
                new ProfessionalReviewJpaEntity();

        entity.id = domain.getId();

        entity.professional = professional;

        entity.userId =
                domain.getUserId();

        entity.appointmentBookingId =
                domain.getAppointmentBookingId();

        entity.reviewAnonymous =
                domain.isReviewAnonymous();

        entity.waitTimeRating =
                domain.getWaitTimeRating();

        entity.mannerRating =
                domain.getMannerRating();

        entity.review =
                domain.getReview();

        entity.overallRating =
                domain.getOverallRating();

        entity.doctorRecommended =
                domain.isDoctorRecommended();

        return entity;
    }

    public static ProfessionalReview toDomain(
            ProfessionalReviewJpaEntity entity
    ) {

        return ProfessionalReview.restore(
                entity.id,
                entity.professional.id,
                entity.userId,
                entity.appointmentBookingId,
                entity.reviewAnonymous,
                entity.waitTimeRating,
                entity.mannerRating,
                entity.review,
                entity.overallRating,
                entity.doctorRecommended
        );
    }
}