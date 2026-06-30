package com.janocare.professional.infrastructure.persistence;

import com.janocare.professional.application.ports.ProfessionalInfoRepositoryPort;
import com.janocare.professional.domain.entities.ProfessionalInfo;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalInfoJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalJpaEntity;
import com.janocare.professional.infrastructure.persistence.mappers.ProfessionalInfoPersistenceMapper;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProfessionalInfoRepository
        implements PanacheRepositoryBase<ProfessionalInfoJpaEntity,UUID>,
        ProfessionalInfoRepositoryPort {

    @Override
    @Transactional
    public ProfessionalInfo save(ProfessionalInfo info) {

        ProfessionalJpaEntity professional =
                getEntityManager().find(
                        ProfessionalJpaEntity.class,
                        info.getProfessionalId()
                );

        if (professional == null) {
            throw new RuntimeException("Professional not found");
        }

        ProfessionalInfoJpaEntity entity =
                ProfessionalInfoPersistenceMapper.toJpaEntity(
                        info,
                        professional
                );

        ProfessionalInfoJpaEntity saved =
                getEntityManager().merge(entity);

        return ProfessionalInfoPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<ProfessionalInfo> findDomainById(UUID id) {

        return findByIdOptional(id)
                .map(ProfessionalInfoPersistenceMapper::toDomain);
    }

    @Override
    public List<ProfessionalInfo> findByProfessionalId(UUID professionalId) {

        return find("professional.id", professionalId)
                .list()
                .stream()
                .map(ProfessionalInfoPersistenceMapper::toDomain)
                .toList();
    }
    @Override
    public List<ProfessionalInfo> findAllProfessionalInfo() {
        return listAll()
                .stream()
                .map(ProfessionalInfoPersistenceMapper::toDomain)
                .toList();
    }
    @Override
    @Transactional
    public void deleteInfoById(UUID id) {
        deleteById(id);
    }
}