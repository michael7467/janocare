package infrastructure.persistence.repositories;

import application.ports.ReviewRepository;
import domain.entities.ProfessionalReview;
import infrastructure.persistence.entities.ProfessionalReviewEntity;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ReviewRepositoryImpl implements ReviewRepository {

    @Inject
    EntityManager em;

    @Override
    public void save(ProfessionalReview review) {
        ProfessionalReviewEntity entity = new ProfessionalReviewEntity();
        entity.setId(review.getId());
        entity.setRating(review.getRating().getValue());
        entity.setComment(review.getComment());
        em.persist(entity);
    }

    @Override
    public List<ProfessionalReview> findByProfessionalId(UUID professionalId) {
        return em.createQuery(
                        "SELECT r FROM ProfessionalReviewEntity r WHERE r.professional.id = :id",
                        ProfessionalReviewEntity.class
                ).setParameter("id", professionalId)
                .getResultList()
                .stream()
                .map(e -> new ProfessionalReview(e.getId(), new domain.valueobjects.Rating(e.getRating()), e.getComment()))
                .toList();
    }
}
