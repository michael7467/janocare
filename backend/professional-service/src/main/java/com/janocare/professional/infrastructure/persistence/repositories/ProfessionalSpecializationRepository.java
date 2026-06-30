package com.janocare.professional.infrastructure.persistence;

import com.janocare.professional.application.ports.ProfessionalSpecializationRepositoryPort;
import com.janocare.professional.domain.entities.ProfessionalSpecialization;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalSpecializationJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.SpecializationJpaEntity;
import com.janocare.professional.infrastructure.persistence.mappers.ProfessionalSpecializationPersistenceMapper;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProfessionalSpecializationRepository
        implements PanacheRepositoryBase<ProfessionalSpecializationJpaEntity,UUID>,
        ProfessionalSpecializationRepositoryPort {

    @Override
    @Transactional
    public ProfessionalSpecialization save(
            ProfessionalSpecialization specialization
    ) {

        ProfessionalJpaEntity professional =
                getEntityManager().find(
                        ProfessionalJpaEntity.class,
                        specialization.getProfessionalId()
                );

        if (professional == null) {
            throw new RuntimeException("Professional not found");
        }

        SpecializationJpaEntity specializationEntity =
                getEntityManager().find(
                        SpecializationJpaEntity.class,
                        specialization.getSpecializationId()
                );

        if (specializationEntity == null) {
            throw new RuntimeException("Specialization not found");
        }

        ProfessionalSpecializationJpaEntity entity =
                ProfessionalSpecializationPersistenceMapper.toJpaEntity(
                        specialization,
                        professional,
                        specializationEntity
                );

        ProfessionalSpecializationJpaEntity saved =
                getEntityManager().merge(entity);

        return ProfessionalSpecializationPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<ProfessionalSpecialization> findDomainById(UUID id) {

        return findByIdOptional(id)
                .map(
                        ProfessionalSpecializationPersistenceMapper::toDomain
                );
    }
    @Override
    public List<ProfessionalSpecialization> findAllProfessionalSpecialization() {
        return listAll()
                .stream()
                .map(
                        ProfessionalSpecializationPersistenceMapper::toDomain
                )
                .toList();
    }
    @Override
    public List<ProfessionalSpecialization> findByProfessionalId(
            UUID professionalId
    ) {

        return find("professional.id", professionalId)
                .list()
                .stream()
                .map(
                        ProfessionalSpecializationPersistenceMapper::toDomain
                )
                .toList();
    }

    @Override
    @Transactional
    public void deleteProfessionalSpecializationById(UUID id) {
        deleteById(id);
    }
}