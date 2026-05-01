package application.handlers;

import application.commands.AddReviewCommand;
import application.ports.ProfessionalRepository;
import application.ports.ReviewRepository;
import domain.entities.ProfessionalReview;
import domain.valueobjects.Rating;

import java.util.UUID;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AddReviewHandler {

    private final ProfessionalRepository professionalRepository;
    private final ReviewRepository reviewRepository;

    public AddReviewHandler(ProfessionalRepository professionalRepository,
                            ReviewRepository reviewRepository) {
        this.professionalRepository = professionalRepository;
        this.reviewRepository = reviewRepository;
    }

    public void handle(AddReviewCommand command) {
        professionalRepository.findById(command.professionalId)
                .orElseThrow(() -> new RuntimeException("Professional not found"));

        ProfessionalReview review = new ProfessionalReview(
                UUID.randomUUID(),
                new Rating(command.rating),
                command.comment
        );

        reviewRepository.save(review);
    }
}
