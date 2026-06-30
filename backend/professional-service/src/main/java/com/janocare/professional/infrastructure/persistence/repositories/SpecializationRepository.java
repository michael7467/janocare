package com.janocare.professional.infrastructure.persistence;

import com.janocare.professional.application.ports.SpecializationRepositoryPort;
import com.janocare.professional.domain.entities.Specialization;
import com.janocare.professional.domain.entities.ProfessionalSubSpecialization;
import com.janocare.professional.infrastructure.persistence.entities.SpecializationJpaEntity;
import com.janocare.professional.infrastructure.persistence.mappers.SpecializationPersistenceMapper;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class SpecializationRepository
        implements PanacheRepositoryBase<SpecializationJpaEntity,UUID>,
        SpecializationRepositoryPort {

    @Override
    @Transactional
    public Specialization save(
            Specialization specialization
    ) {

        SpecializationJpaEntity entity =
                SpecializationPersistenceMapper.toJpaEntity(
                        specialization
                );

        SpecializationJpaEntity saved =
                getEntityManager().merge(entity);

        return SpecializationPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Specialization> findDomainById(UUID id) {

        return findByIdOptional(id)
                .map(
                        SpecializationPersistenceMapper::toDomain
                );
    }

    @Override
    public Optional<Specialization> findByName(String name) {

        return find("name", name)
                .firstResultOptional()
                .map(
                        SpecializationPersistenceMapper::toDomain
                );
    }

    @Override
    public List<Specialization> findAllSpecializations() {

        return listAll()
                .stream()
                .map(
                        SpecializationPersistenceMapper::toDomain
                )
                .toList();
    }

    @Override
    @Transactional
    public void deleteSpecializationById(UUID id) {
        deleteById(id);
    }
}