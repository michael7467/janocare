package com.janocare.professional.application.commands.professionalreview;

import com.janocare.professional.domain.enums.RatingScale;
import java.math.BigDecimal;
import java.util.UUID;

public class CreateProfessionalReviewCommand {

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