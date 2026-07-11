package com.janocare.professional.infrastructure.persistence;

import com.janocare.professional.application.ports.ProfessionTypeRepositoryPort;
import com.janocare.professional.domain.entities.ProfessionType;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionTypeJpaEntity;
import com.janocare.professional.infrastructure.persistence.mappers.ProfessionTypePersistenceMapper;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProfessionTypeRepository
        implements PanacheRepositoryBase<ProfessionTypeJpaEntity, UUID>,
                   ProfessionTypeRepositoryPort {

    private static final Logger LOG =
            Logger.getLogger(ProfessionTypeRepository.class);

    // ── SAVE ─────────────────────────────────────────────────
    // persist for new entities, merge for existing
    // same pattern as ProfessionalRepository.save()
    @Override
    @Transactional
    public ProfessionType save(ProfessionType professionType) {

        ProfessionTypeJpaEntity entity =
                ProfessionTypePersistenceMapper.toJpaEntity(professionType);

        // check if entity already exists
        ProfessionTypeJpaEntity existing =
                getEntityManager().find(
                        ProfessionTypeJpaEntity.class,
                        professionType.getId()
                );

        ProfessionTypeJpaEntity saved;
        if (existing == null) {
            getEntityManager().persist(entity);
            saved = entity;
            LOG.debugf("ProfessionType persisted: %s", entity.name);
        } else {
            saved = getEntityManager().merge(entity);
            LOG.debugf("ProfessionType merged: %s", entity.name);
        }

        return ProfessionTypePersistenceMapper.toDomain(saved);
    }

    // ── FIND BY ID ───────────────────────────────────────────
    @Override
    public Optional<ProfessionType> findDomainById(UUID id) {
        return findByIdOptional(id)
                .map(ProfessionTypePersistenceMapper::toDomain);
    }

    // ── FIND BY NAME ─────────────────────────────────────────
    // Used by handler to check for duplicate names before create/update
    // Field name must match JPA entity field — "name" not "type"
    @Override
    public Optional<ProfessionType> findByName(String name) {
        return find("name", name)
                .firstResultOptional()
                .map(ProfessionTypePersistenceMapper::toDomain);
    }

    // ── FIND ALL ─────────────────────────────────────────────
    @Override
    public List<ProfessionType> findAllProfessionTypes() {
        return listAll()
                .stream()
                .map(ProfessionTypePersistenceMapper::toDomain)
                .toList();
    }

    // ── DELETE ───────────────────────────────────────────────
    @Override
    @Transactional
    public void deleteProfessionTypeById(UUID id) {
        deleteById(id);
        LOG.infof("ProfessionType deleted: %s", id);
    }
}