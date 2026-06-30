package com.janocare.professional.infrastructure.persistence;

import com.janocare.professional.application.ports.ProfessionalRepositoryPort;
import com.janocare.professional.domain.entities.Professional;
import com.janocare.professional.domain.exceptions.ProfessionTypeNotFoundException;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionTypeJpaEntity;
import com.janocare.professional.infrastructure.persistence.mappers.ProfessionalPersistenceMapper;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProfessionalRepository
        implements PanacheRepositoryBase<ProfessionalJpaEntity,UUID>, ProfessionalRepositoryPort {

    @Override
    @Transactional
    public Professional save(Professional professional) {
        ProfessionTypeJpaEntity professionType =
                getEntityManager().find(
                        ProfessionTypeJpaEntity.class,
                        professional.getProfessionTypeId()
                );

        if (professionType == null) {
            throw new ProfessionTypeNotFoundException();
        }

        ProfessionalJpaEntity entity =
                ProfessionalPersistenceMapper.toJpaEntity(
                        professional,
                        professionType
                );

        ProfessionalJpaEntity saved =
                getEntityManager().merge(entity);

        return ProfessionalPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Professional> findDomainById(UUID id) {
        return findByIdOptional(id)
                .map(ProfessionalPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Professional> findByUserId(UUID userId) {
        return find("userId", userId)
                .firstResultOptional()
                .map(ProfessionalPersistenceMapper::toDomain);
    }

    @Override
    public List<Professional> findAllProfessionals() {
        return listAll()
                .stream()
                .map(ProfessionalPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteProfessionalById(UUID id) {
        deleteById(id);
    }
}