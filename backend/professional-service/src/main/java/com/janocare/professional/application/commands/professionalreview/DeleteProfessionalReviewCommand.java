package com.janocare.professional.application.commands.professionalreview;

import java.util.UUID;

public class DeleteProfessionalReviewCommand {

    public UUID reviewId;

    public DeleteProfessionalReviewCommand() {}

    public DeleteProfessionalReviewCommand(UUID reviewId) {
        this.reviewId = reviewId;
    }
}