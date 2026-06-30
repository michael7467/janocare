package com.janocare.professional.infrastructure.persistence;

import com.janocare.professional.application.ports.ProfessionalAchievementRepositoryPort;
import com.janocare.professional.domain.entities.ProfessionalAchievement;
import com.janocare.professional.domain.exceptions.ProfessionalNotFoundException;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalAchievementJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalJpaEntity;
import com.janocare.professional.infrastructure.persistence.mappers.ProfessionalAchievementPersistenceMapper;


import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProfessionalAchievementRepository
        implements PanacheRepositoryBase<ProfessionalAchievementJpaEntity,UUID>,
        ProfessionalAchievementRepositoryPort {

    @Override
    @Transactional
    public ProfessionalAchievement save(ProfessionalAchievement achievement) {
        ProfessionalJpaEntity professional =
                getEntityManager().find(
                        ProfessionalJpaEntity.class,
                        achievement.getProfessionalId()
                );

        if (professional == null) {
            throw new ProfessionalNotFoundException();
        }

        ProfessionalAchievementJpaEntity entity =
                ProfessionalAchievementPersistenceMapper.toJpaEntity(
                        achievement,
                        professional
                );

        ProfessionalAchievementJpaEntity saved =
                getEntityManager().merge(entity);

        return ProfessionalAchievementPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<ProfessionalAchievement> findDomainById(UUID id) {
        return findByIdOptional(id)
                .map(ProfessionalAchievementPersistenceMapper::toDomain);
    }

    @Override
    public List<ProfessionalAchievement> findByProfessionalId(UUID professionalId) {
        return find("professional.id", professionalId)
                .list()
                .stream()
                .map(ProfessionalAchievementPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<ProfessionalAchievement> findAllAchievements() {
        return listAll()
                .stream()
                .map(ProfessionalAchievementPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteAchievementById(UUID id) {
        deleteById(id);
    }
}