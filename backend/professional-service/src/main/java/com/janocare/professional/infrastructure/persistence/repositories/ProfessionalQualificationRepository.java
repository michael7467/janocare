package com.janocare.professional.infrastructure.persistence;

import com.janocare.professional.application.ports.ProfessionalQualificationRepositoryPort;
import com.janocare.professional.domain.entities.ProfessionalQualification;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalQualificationJpaEntity;
import com.janocare.professional.infrastructure.persistence.mappers.ProfessionalQualificationPersistenceMapper;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProfessionalQualificationRepository
        implements PanacheRepositoryBase<ProfessionalQualificationJpaEntity,UUID>,
        ProfessionalQualificationRepositoryPort {

    @Override
    @Transactional
    public ProfessionalQualification save(
            ProfessionalQualification qualification
    ) {

        ProfessionalJpaEntity professional =
                getEntityManager().find(
                        ProfessionalJpaEntity.class,
                        qualification.getProfessionalId()
                );

        if (professional == null) {
            throw new RuntimeException("Professional not found");
        }

        ProfessionalQualificationJpaEntity entity =
                ProfessionalQualificationPersistenceMapper.toJpaEntity(
                        qualification,
                        professional
                );

        ProfessionalQualificationJpaEntity saved =
                getEntityManager().merge(entity);

        return ProfessionalQualificationPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<ProfessionalQualification> findDomainById(UUID id) {

        return findByIdOptional(id)
                .map(
                        ProfessionalQualificationPersistenceMapper::toDomain
                );
    }

    @Override
    public List<ProfessionalQualification> findByProfessionalId(
            UUID professionalId
    ) {

        return find("professional.id", professionalId)
                .list()
                .stream()
                .map(
                        ProfessionalQualificationPersistenceMapper::toDomain
                )
                .toList();
    }
    @Override
    public List<ProfessionalQualification> findAllQualifications() {
        return listAll()
                .stream()
                .map(ProfessionalQualificationPersistenceMapper::toDomain)
                .toList();
    }
    @Override
    @Transactional
    public void deleteQualificationById(UUID id) {
        deleteById(id);
    }
}