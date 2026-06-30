package com.janocare.professional.api.responses.professionalreview;
import com.janocare.professional.domain.enums.RatingScale;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.UUID;

public class ProfessionalReviewResponse {

    public UUID id;

    public UUID professionalId;

    public UUID userId;

    public UUID appointmentBookingId;

    public boolean reviewAnonymous;

    public RatingScale waitTimeRating;

    public RatingScale mannerRating;

    public String review;

    public BigDecimal overallRating;

    public boolean doctorRecommended;

    public LocalDateTime createdAt;

    public LocalDateTime updatedAt;
}