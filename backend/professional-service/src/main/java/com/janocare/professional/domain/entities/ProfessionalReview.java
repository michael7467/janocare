package com.janocare.professional.domain.entities;

import com.janocare.professional.domain.enums.RatingScale;

import java.math.BigDecimal;
import java.util.UUID;

public class ProfessionalReview {

    private UUID id;

    private UUID professionalId;

    // The patient/user who wrote the review from auth-service
    private UUID userId;

    // Booking ID from booking-service
    private UUID appointmentBookingId;

    private boolean reviewAnonymous;

    private RatingScale waitTimeRating;

    private RatingScale mannerRating;

    private String review;

    private BigDecimal overallRating;

    private boolean doctorRecommended;

    protected ProfessionalReview() {}

    public static ProfessionalReview create(
            UUID professionalId,
            UUID userId,
            UUID appointmentBookingId,
            boolean reviewAnonymous,
            RatingScale waitTimeRating,
            RatingScale mannerRating,
            String review,
            BigDecimal overallRating,
            boolean doctorRecommended
    ) {
        ProfessionalReview r = new ProfessionalReview();

        r.id = UUID.randomUUID();
        r.professionalId = professionalId;
        r.userId = userId;
        r.appointmentBookingId = appointmentBookingId;
        r.reviewAnonymous = reviewAnonymous;
        r.waitTimeRating = waitTimeRating;
        r.mannerRating = mannerRating;
        r.review = review;
        r.overallRating = overallRating != null ? overallRating : BigDecimal.ZERO;
        r.doctorRecommended = doctorRecommended;

        return r;
    }

    public static ProfessionalReview restore(
            UUID id,
            UUID professionalId,
            UUID userId,
            UUID appointmentBookingId,
            boolean reviewAnonymous,
            RatingScale waitTimeRating,
            RatingScale mannerRating,
            String review,
            BigDecimal overallRating,
            boolean doctorRecommended
    ) {
        ProfessionalReview r = new ProfessionalReview();

        r.id = id;
        r.professionalId = professionalId;
        r.userId = userId;
        r.appointmentBookingId = appointmentBookingId;
        r.reviewAnonymous = reviewAnonymous;
        r.waitTimeRating = waitTimeRating;
        r.mannerRating = mannerRating;
        r.review = review;
        r.overallRating = overallRating;
        r.doctorRecommended = doctorRecommended;

        return r;
    }
    public void update(
            boolean reviewAnonymous,
            RatingScale waitTimeRating,
            RatingScale mannerRating,
            String review,
            BigDecimal overallRating,
            boolean doctorRecommended
    ) {

        this.reviewAnonymous = reviewAnonymous;

        this.waitTimeRating = waitTimeRating;

        this.mannerRating = mannerRating;

        this.review = review;

        this.overallRating = overallRating;

        this.doctorRecommended = doctorRecommended;
    }
    public UUID getId() {
        return id;
    }

    public UUID getProfessionalId() {
        return professionalId;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getAppointmentBookingId() {
        return appointmentBookingId;
    }

    public boolean isReviewAnonymous() {
        return reviewAnonymous;
    }

    public RatingScale getWaitTimeRating() {
        return waitTimeRating;
    }

    public RatingScale getMannerRating() {
        return mannerRating;
    }

    public String getReview() {
        return review;
    }

    public BigDecimal getOverallRating() {
        return overallRating;
    }

    public boolean isDoctorRecommended() {
        return doctorRecommended;
    }

}