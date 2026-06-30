package com.janocare.professional.infrastructure.persistence;

import com.janocare.professional.application.ports.ProfessionTypeRepositoryPort;
import com.janocare.professional.domain.entities.ProfessionType;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionTypeJpaEntity;
import com.janocare.professional.infrastructure.persistence.mappers.ProfessionTypePersistenceMapper;
import jakarta.transaction.Transactional;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProfessionTypeRepository
        implements PanacheRepositoryBase<ProfessionTypeJpaEntity,UUID>, ProfessionTypeRepositoryPort {

    @Override
    public ProfessionType save(ProfessionType professionType) {
        ProfessionTypeJpaEntity entity =
                ProfessionTypePersistenceMapper.toJpaEntity(professionType);

        persist(entity);

        return ProfessionTypePersistenceMapper.toDomain(entity);
    }

    @Override
    public Optional<ProfessionType> findDomainById(UUID id) {
        return findByIdOptional(id)
                .map(ProfessionTypePersistenceMapper::toDomain);
    }

    @Override
    public Optional<ProfessionType> findByType(String type) {
        return find("type", type)
                .firstResultOptional()
                .map(ProfessionTypePersistenceMapper::toDomain);
    }


    @Override
    public List<ProfessionType> findAllProfessionTypes() {
        return listAll()
                .stream()
                .map(ProfessionTypePersistenceMapper::toDomain)
                .toList();
    }
    @Override
    @Transactional
    public void deleteProfessionTypeById(UUID id) {
        deleteById(id);
    }
}