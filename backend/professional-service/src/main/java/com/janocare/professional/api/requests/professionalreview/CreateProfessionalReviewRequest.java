package com.janocare.professional.api.requests.professionalreview;

import com.janocare.professional.domain.enums.RatingScale;
import java.math.BigDecimal;
import java.util.UUID;

public class CreateProfessionalReviewRequest {

    public UUID professionalId;

    public UUID userId;

    public UUID appointmentBookingId;

    public boolean reviewAnonymous;

    public RatingScale waitTimeRating;

    public RatingScale mannerRating;

    public String review;

    public BigDecimal overallRating;

    public boolean doctorRecommended;
}