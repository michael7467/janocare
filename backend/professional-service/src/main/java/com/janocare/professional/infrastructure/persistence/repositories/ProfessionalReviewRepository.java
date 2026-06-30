package com.janocare.professional.infrastructure.persistence;

import com.janocare.professional.application.ports.ProfessionalReviewRepositoryPort;
import com.janocare.professional.domain.entities.ProfessionalReview;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalReviewJpaEntity;
import com.janocare.professional.infrastructure.persistence.mappers.ProfessionalReviewPersistenceMapper;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProfessionalReviewRepository
        implements PanacheRepositoryBase<ProfessionalReviewJpaEntity,UUID>,
        ProfessionalReviewRepositoryPort {

    @Override
    @Transactional
    public ProfessionalReview save(
            ProfessionalReview review
    ) {

        ProfessionalJpaEntity professional =
                getEntityManager().find(
                        ProfessionalJpaEntity.class,
                        review.getProfessionalId()
                );

        if (professional == null) {
            throw new RuntimeException("Professional not found");
        }

        ProfessionalReviewJpaEntity entity =
                ProfessionalReviewPersistenceMapper.toJpaEntity(
                        review,
                        professional
                );

        ProfessionalReviewJpaEntity saved =
                getEntityManager().merge(entity);

        return ProfessionalReviewPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<ProfessionalReview> findDomainById(UUID id) {

        return findByIdOptional(id)
                .map(
                        ProfessionalReviewPersistenceMapper::toDomain
                );
    }

    @Override
    public List<ProfessionalReview> findByProfessionalId(
            UUID professionalId
    ) {

        return find("professional.id", professionalId)
                .list()
                .stream()
                .map(
                        ProfessionalReviewPersistenceMapper::toDomain
                )
                .toList();
    }
    @Override
    public List<ProfessionalReview> findAllProfessionalReviews() {
        return listAll()
                .stream()
                .map(ProfessionalReviewPersistenceMapper::toDomain)
                .toList();
    }
    @Override
    @Transactional
    public void deleteReviewById(UUID id) {
        deleteById(id);
    }
}