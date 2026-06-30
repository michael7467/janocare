package com.janocare.professional.application.handlers;

import com.janocare.professional.api.mappers.ProfessionalReviewApiMapper;
import com.janocare.professional.api.responses.professionalreview.ProfessionalReviewResponse;
import com.janocare.professional.application.commands.professionalreview.CreateProfessionalReviewCommand;
import com.janocare.professional.application.commands.professionalreview.DeleteProfessionalReviewCommand;
import com.janocare.professional.application.commands.professionalreview.UpdateProfessionalReviewCommand;
import com.janocare.professional.application.ports.ProfessionalRepositoryPort;
import com.janocare.professional.application.ports.ProfessionalReviewRepositoryPort;
import com.janocare.professional.application.queries.professionalreview.FindAllProfessionalReviewsQuery;
import com.janocare.professional.application.queries.professionalreview.FindProfessionalReviewByIdQuery;
import com.janocare.professional.application.queries.professionalreview.FindProfessionalReviewsByProfessionalIdQuery;
import com.janocare.professional.domain.entities.ProfessionalReview;
import com.janocare.professional.domain.exceptions.ProfessionalNotFoundException;
import com.janocare.professional.domain.exceptions.ValidationException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ProfessionalReviewHandler {

    @Inject
    ProfessionalReviewRepositoryPort reviewRepository;

    @Inject
    ProfessionalRepositoryPort professionalRepository;

    @Transactional
    public ProfessionalReviewResponse create(
            CreateProfessionalReviewCommand command
    ) {

        if (command == null) {
            throw new ValidationException("Command body is required");
        }

        if (command.professionalId == null) {
            throw new ValidationException("Professional ID is required");
        }

        if (command.userId == null) {
            throw new ValidationException("User ID is required");
        }

        professionalRepository.findDomainById(command.professionalId)
                .orElseThrow(ProfessionalNotFoundException::new);

        ProfessionalReview review =
                ProfessionalReview.create(
                        command.professionalId,
                        command.userId,
                        command.appointmentBookingId,
                        command.reviewAnonymous,
                        command.waitTimeRating,
                        command.mannerRating,
                        command.review,
                        command.overallRating,
                        command.doctorRecommended
                );

        ProfessionalReview saved =
                reviewRepository.save(review);

        return ProfessionalReviewApiMapper.toResponse(saved);
    }

    @Transactional
    public ProfessionalReviewResponse update(
            UpdateProfessionalReviewCommand command
    ) {

        if (command == null) {
            throw new ValidationException("Command body is required");
        }

        if (command.reviewId == null) {
            throw new ValidationException("Review ID is required");
        }

        ProfessionalReview review =
                reviewRepository.findDomainById(command.reviewId)
                        .orElseThrow(() ->
                                new ValidationException("Review not found"));

        review.update(
                command.reviewAnonymous,
                command.waitTimeRating,
                command.mannerRating,
                command.review,
                command.overallRating,
                command.doctorRecommended
        );

        ProfessionalReview saved =
                reviewRepository.save(review);

        return ProfessionalReviewApiMapper.toResponse(saved);
    }

    @Transactional
    public void delete(
            DeleteProfessionalReviewCommand command
    ) {

        if (command == null || command.reviewId == null) {
            throw new ValidationException("Review ID is required");
        }

        reviewRepository.findDomainById(command.reviewId)
                .orElseThrow(() ->
                        new ValidationException("Review not found"));

        reviewRepository.deleteReviewById(command.reviewId);
    }

    public ProfessionalReviewResponse findById(
            FindProfessionalReviewByIdQuery query
    ) {

        if (query == null || query.reviewId == null) {
            throw new ValidationException("Review ID is required");
        }

        ProfessionalReview review =
                reviewRepository.findDomainById(query.reviewId)
                        .orElseThrow(() ->
                                new ValidationException("Review not found"));

        return ProfessionalReviewApiMapper.toResponse(review);
    }

    public List<ProfessionalReviewResponse> findByProfessionalId(
            FindProfessionalReviewsByProfessionalIdQuery query
    ) {

        if (query == null || query.professionalId == null) {
            throw new ValidationException("Professional ID is required");
        }

        professionalRepository.findDomainById(query.professionalId)
                .orElseThrow(ProfessionalNotFoundException::new);

        return reviewRepository.findByProfessionalId(query.professionalId)
                .stream()
                .map(ProfessionalReviewApiMapper::toResponse)
                .toList();
    }

    public List<ProfessionalReviewResponse> findAll(
            FindAllProfessionalReviewsQuery query
    ) {

        return reviewRepository.findAllProfessionalReviews()
                .stream()
                .map(ProfessionalReviewApiMapper::toResponse)
                .toList();
    }
}