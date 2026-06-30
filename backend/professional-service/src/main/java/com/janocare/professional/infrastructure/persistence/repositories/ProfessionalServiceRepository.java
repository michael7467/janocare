package com.janocare.professional.infrastructure.persistence;

import com.janocare.professional.application.ports.ProfessionalServiceRepositoryPort;
import com.janocare.professional.domain.entities.ProfessionalService;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalServiceJpaEntity;
import com.janocare.professional.infrastructure.persistence.mappers.ProfessionalServicePersistenceMapper;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProfessionalServiceRepository
        implements PanacheRepositoryBase<ProfessionalServiceJpaEntity,UUID>,
        ProfessionalServiceRepositoryPort {

    @Override
    @Transactional
    public ProfessionalService save(ProfessionalService service) {
        ProfessionalJpaEntity professional =
                getEntityManager().find(
                        ProfessionalJpaEntity.class,
                        service.getProfessionalId()
                );

        if (professional == null) {
            throw new RuntimeException("Professional not found");
        }

        ProfessionalServiceJpaEntity entity =
                ProfessionalServicePersistenceMapper.toJpaEntity(
                        service,
                        professional
                );

        ProfessionalServiceJpaEntity saved =
                getEntityManager().merge(entity);

        return ProfessionalServicePersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<ProfessionalService> findDomainById(UUID id) {
        return findByIdOptional(id)
                .map(ProfessionalServicePersistenceMapper::toDomain);
    }

    @Override
    public List<ProfessionalService> findByProfessionalId(UUID professionalId) {
        return find("professional.id", professionalId)
                .list()
                .stream()
                .map(ProfessionalServicePersistenceMapper::toDomain)
                .toList();
    }
    @Override
    public List<ProfessionalService> findAllProfessionalServices() {
        return listAll()
                .stream()
                .map(ProfessionalServicePersistenceMapper::toDomain)
                .toList();
    }
    @Override
    @Transactional
    public void deleteServiceById(UUID id) {
        deleteById(id);
    }
}