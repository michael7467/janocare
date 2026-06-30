package com.janocare.professional.application.ports;

import com.janocare.professional.domain.entities.ProfessionalReview;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfessionalReviewRepositoryPort {

    ProfessionalReview save(ProfessionalReview review);

    Optional<ProfessionalReview> findDomainById(UUID id);

    List<ProfessionalReview> findByProfessionalId(UUID professionalId);

    List<ProfessionalReview> findAllProfessionalReviews();

    void deleteReviewById(UUID id);
}