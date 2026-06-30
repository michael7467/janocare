package com.janocare.professional.infrastructure.persistence;

import com.janocare.professional.application.ports.SubSpecializationRepositoryPort;
import com.janocare.professional.domain.entities.SubSpecialization;
import com.janocare.professional.infrastructure.persistence.entities.SpecializationJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.SubSpecializationJpaEntity;
import com.janocare.professional.infrastructure.persistence.mappers.SubSpecializationPersistenceMapper;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class SubSpecializationRepository
        implements PanacheRepositoryBase<SubSpecializationJpaEntity,UUID>,
        SubSpecializationRepositoryPort {

    @Override
    @Transactional
    public SubSpecialization save(
            SubSpecialization subSpecialization
    ) {

        SpecializationJpaEntity specialization =
                getEntityManager().find(
                        SpecializationJpaEntity.class,
                        subSpecialization.getSpecializationId()
                );

        if (specialization == null) {
            throw new RuntimeException("Specialization not found");
        }

        SubSpecializationJpaEntity entity =
                SubSpecializationPersistenceMapper.toJpaEntity(
                        subSpecialization,
                        specialization
                );

        SubSpecializationJpaEntity saved =
                getEntityManager().merge(entity);

        return SubSpecializationPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<SubSpecialization> findDomainById(UUID id) {

        return findByIdOptional(id)
                .map(
                        SubSpecializationPersistenceMapper::toDomain
                );
    }

    @Override
    public List<SubSpecialization> findBySpecializationId(
            UUID specializationId
    ) {

        return find("specialization.id", specializationId)
                .list()
                .stream()
                .map(
                        SubSpecializationPersistenceMapper::toDomain
                )
                .toList();
    }

    @Override
    public Optional<SubSpecialization> findByName(String name) {

        return find("name", name)
                .firstResultOptional()
                .map(
                        SubSpecializationPersistenceMapper::toDomain
                );
    }
    @Override
    public List<SubSpecialization> findAllSubSpecializations() {

        return listAll()
                .stream()
                .map(
                        SubSpecializationPersistenceMapper::toDomain
                )
                .toList();
    }
    @Override
    @Transactional
    public void deleteSubSpecializationById(UUID id) {
        deleteById(id);
    }
}