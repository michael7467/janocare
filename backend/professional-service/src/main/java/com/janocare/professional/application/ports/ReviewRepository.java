package application.ports;

import domain.entities.ProfessionalReview;

import java.util.UUID;
import java.util.List;

public interface ReviewRepository {
    void save(ProfessionalReview review);
    List<ProfessionalReview> findByProfessionalId(UUID professionalId);
}
