package com.janocare.professional.infrastructure.persistence;

import com.janocare.professional.application.ports.ProfessionalSubSpecializationRepositoryPort;
import com.janocare.professional.domain.entities.ProfessionalSubSpecialization;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalSubSpecializationJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.SubSpecializationJpaEntity;
import com.janocare.professional.infrastructure.persistence.mappers.ProfessionalSubSpecializationPersistenceMapper;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProfessionalSubSpecializationRepository
        implements PanacheRepositoryBase<ProfessionalSubSpecializationJpaEntity,UUID>,
        ProfessionalSubSpecializationRepositoryPort {

    @Override
    @Transactional
    public ProfessionalSubSpecialization save(
            ProfessionalSubSpecialization specialization
    ) {

        ProfessionalJpaEntity professional =
                getEntityManager().find(
                        ProfessionalJpaEntity.class,
                        specialization.getProfessionalId()
                );

        if (professional == null) {
            throw new RuntimeException("Professional not found");
        }

        SubSpecializationJpaEntity subSpecialization =
                getEntityManager().find(
                        SubSpecializationJpaEntity.class,
                        specialization.getSubSpecializationId()
                );

        if (subSpecialization == null) {
            throw new RuntimeException("Sub specialization not found");
        }

        ProfessionalSubSpecializationJpaEntity entity =
                ProfessionalSubSpecializationPersistenceMapper.toJpaEntity(
                        specialization,
                        professional,
                        subSpecialization
                );

        ProfessionalSubSpecializationJpaEntity saved =
                getEntityManager().merge(entity);

        return ProfessionalSubSpecializationPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<ProfessionalSubSpecialization> findDomainById(UUID id) {

        return findByIdOptional(id)
                .map(
                        ProfessionalSubSpecializationPersistenceMapper::toDomain
                );
    }

    @Override
    public List<ProfessionalSubSpecialization> findByProfessionalId(
            UUID professionalId
    ) {

        return find("professional.id", professionalId)
                .list()
                .stream()
                .map(
                        ProfessionalSubSpecializationPersistenceMapper::toDomain
                )
                .toList();
    }
    @Override
    public List<ProfessionalSubSpecialization> findAllProfessionalSubSpecialization() {
        return listAll()
                .stream()
                .map(
                        ProfessionalSubSpecializationPersistenceMapper::toDomain
                )
                .toList();
    }

    @Override
    @Transactional
    public void deleteProfessionalSubSpecializationById(UUID id) {
        deleteById(id);
    }
}