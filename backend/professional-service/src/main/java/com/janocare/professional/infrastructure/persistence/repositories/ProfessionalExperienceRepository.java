package com.janocare.professional.infrastructure.persistence;

import com.janocare.professional.application.ports.ProfessionalExperienceRepositoryPort;
import com.janocare.professional.domain.entities.ProfessionalExperience;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalExperienceJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalJpaEntity;
import com.janocare.professional.infrastructure.persistence.mappers.ProfessionalExperiencePersistenceMapper;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProfessionalExperienceRepository
        implements PanacheRepositoryBase<ProfessionalExperienceJpaEntity,UUID>,
        ProfessionalExperienceRepositoryPort {

    @Override
    @Transactional
    public ProfessionalExperience save(ProfessionalExperience experience) {
        ProfessionalJpaEntity professional =
                getEntityManager().find(ProfessionalJpaEntity.class, experience.getProfessionalId());

        if (professional == null) {
            throw new RuntimeException("Professional not found");
        }

        ProfessionalExperienceJpaEntity entity =
                ProfessionalExperiencePersistenceMapper.toJpaEntity(experience, professional);

        ProfessionalExperienceJpaEntity saved = getEntityManager().merge(entity);

        return ProfessionalExperiencePersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<ProfessionalExperience> findDomainById(UUID id) {
        return findByIdOptional(id)
                .map(ProfessionalExperiencePersistenceMapper::toDomain);
    }

    @Override
    public List<ProfessionalExperience> findByProfessionalId(UUID professionalId) {
        return find("professional.id", professionalId)
                .list()
                .stream()
                .map(ProfessionalExperiencePersistenceMapper::toDomain)
                .toList();
    }
    @Override
    public List<ProfessionalExperience> findAllExperiences() {
        return listAll()
                .stream()
                .map(ProfessionalExperiencePersistenceMapper::toDomain)
                .toList();
    }
    @Override
    @Transactional
    public void deleteExperienceById(UUID id) {
        deleteById(id);
    }
}