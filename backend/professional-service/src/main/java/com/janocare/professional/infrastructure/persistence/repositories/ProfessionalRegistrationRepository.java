package com.janocare.professional.infrastructure.persistence;

import com.janocare.professional.application.ports.ProfessionalRegistrationRepositoryPort;
import com.janocare.professional.domain.entities.ProfessionalRegistration;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalRegistrationJpaEntity;
import com.janocare.professional.infrastructure.persistence.mappers.ProfessionalRegistrationPersistenceMapper;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProfessionalRegistrationRepository
        implements PanacheRepositoryBase<ProfessionalRegistrationJpaEntity,UUID>,
        ProfessionalRegistrationRepositoryPort {

    @Override
    @Transactional
    public ProfessionalRegistration save(
            ProfessionalRegistration registration
    ) {

        ProfessionalJpaEntity professional =
                getEntityManager().find(
                        ProfessionalJpaEntity.class,
                        registration.getProfessionalId()
                );

        if (professional == null) {
            throw new RuntimeException("Professional not found");
        }

        ProfessionalRegistrationJpaEntity entity =
                ProfessionalRegistrationPersistenceMapper.toJpaEntity(
                        registration,
                        professional
                );

        ProfessionalRegistrationJpaEntity saved =
                getEntityManager().merge(entity);

        return ProfessionalRegistrationPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<ProfessionalRegistration> findDomainById(UUID id) {

        return findByIdOptional(id)
                .map(
                        ProfessionalRegistrationPersistenceMapper::toDomain
                );
    }

    @Override
    public List<ProfessionalRegistration> findByProfessionalId(
            UUID professionalId
    ) {

        return find("professional.id", professionalId)
                .list()
                .stream()
                .map(
                        ProfessionalRegistrationPersistenceMapper::toDomain
                )
                .toList();
    }
    @Override
    public List<ProfessionalRegistration> findAllProfessionalRegistrations() {
        return listAll()
                .stream()
                .map(ProfessionalRegistrationPersistenceMapper::toDomain)
                .toList();
    }
    @Override
    @Transactional
    public void deleteRegistrationById(UUID id) {
        deleteById(id);
    }
}