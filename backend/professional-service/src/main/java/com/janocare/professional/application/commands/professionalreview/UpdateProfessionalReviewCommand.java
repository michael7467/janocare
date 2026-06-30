package com.janocare.professional.application.commands.professionalreview;

import com.janocare.professional.domain.enums.RatingScale;
import java.math.BigDecimal;
import java.util.UUID;

public class UpdateProfessionalReviewCommand {

    public UUID reviewId;

    public boolean reviewAnonymous;

    public RatingScale waitTimeRating;

    public RatingScale mannerRating;

    public String review;

    public BigDecimal overallRating;

    public boolean doctorRecommended;
}