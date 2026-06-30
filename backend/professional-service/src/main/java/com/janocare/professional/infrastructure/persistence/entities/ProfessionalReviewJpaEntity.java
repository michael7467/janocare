package com.janocare.professional.infrastructure.persistence.entities;

import com.janocare.professional.domain.enums.RatingScale;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "professional_reviews")
public class ProfessionalReviewJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    public UUID id;

    @Column(name = "professional_id", nullable = false)
    public UUID professionalId;

    @Column(name = "user_id", nullable = false)
    public UUID userId;

    @Column(name = "appointment_booking_id")
    public UUID appointmentBookingId;

    @Column(name = "is_review_anonymous", nullable = false)
    public boolean reviewAnonymous;

    @Enumerated(EnumType.STRING)
    @Column(name = "wait_time_rating", nullable = false)
    public RatingScale waitTimeRating;

    @Enumerated(EnumType.STRING)
    @Column(name = "manner_rating", nullable = false)
    public RatingScale mannerRating;

    @Column(length = 1024)
    public String review;

    @Column(name = "overall_rating", precision = 10, scale = 2, nullable = false)
    public BigDecimal overallRating = BigDecimal.ZERO;

    @Column(name = "is_doctor_recommended", nullable = false)
    public boolean doctorRecommended;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "professional_id",
            referencedColumnName = "id",
            insertable = false,
            updatable = false
    )
    public ProfessionalJpaEntity professional;
}