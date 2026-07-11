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
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProfessionalRepository
        implements PanacheRepositoryBase<ProfessionalJpaEntity, UUID>,
                   ProfessionalRepositoryPort {

    private static final Logger LOG =
            Logger.getLogger(ProfessionalRepository.class);

    // ── SAVE ─────────────────────────────────────────────────
    // Data Mapper pattern:
    // 1. Load ProfessionType JPA entity for the @ManyToOne reference
    // 2. Convert domain entity → JPA entity via mapper
    // 3. Persist or merge depending on whether entity is new
    // 4. Convert result back to domain entity
    @Override
    @Transactional
    public Professional save(Professional professional) {

        // load ProfessionType for the real FK relationship
        // ProfessionType = knowledge level (Accountability pattern)
        ProfessionTypeJpaEntity professionType =
                getEntityManager().find(
                        ProfessionTypeJpaEntity.class,
                        professional.getProfessionTypeId()
                );

        if (professionType == null) {
            throw new ProfessionTypeNotFoundException(
                    "Profession type not found: "
                            + professional.getProfessionTypeId());
        }

        ProfessionalJpaEntity entity =
                ProfessionalPersistenceMapper.toJpaEntity(
                        professional, professionType);

        // use persist for new entities, merge for existing
        // avoids detached entity exceptions
        ProfessionalJpaEntity existing =
                getEntityManager().find(
                        ProfessionalJpaEntity.class,
                        professional.getId()
                );

        ProfessionalJpaEntity saved;
        if (existing == null) {
            getEntityManager().persist(entity);
            saved = entity;
            LOG.debugf("Professional persisted: %s", entity.id);
        } else {
            saved = getEntityManager().merge(entity);
            LOG.debugf("Professional merged: %s", entity.id);
        }

        return ProfessionalPersistenceMapper.toDomain(saved);
    }

    // ── FIND BY ID ───────────────────────────────────────────
    @Override
    public Optional<Professional> findDomainById(UUID id) {
        return findByIdOptional(id)
                .map(ProfessionalPersistenceMapper::toDomain);
    }

    // ── FIND BY USER ID ──────────────────────────────────────
    // userId is a Reference by Identity — cross-context UUID
    // from auth service, not a real FK in this database
    @Override
    public Optional<Professional> findByUserId(UUID userId) {
        return find("userId", userId)
                .firstResultOptional()
                .map(ProfessionalPersistenceMapper::toDomain);
    }

    // ── FIND ALL ─────────────────────────────────────────────
    @Override
    public List<Professional> findAllProfessionals() {
        return listAll()
                .stream()
                .map(ProfessionalPersistenceMapper::toDomain)
                .toList();
    }

    // ── FIND BY STATUS ───────────────────────────────────────
    // Used by admin to list PENDING professionals for approval
    public List<Professional> findByStatus(String status) {
        return find("status", status)
                .stream()
                .map(ProfessionalPersistenceMapper::toDomain)
                .toList();
    }

    // ── DELETE ───────────────────────────────────────────────
    @Override
    @Transactional
    public void deleteProfessionalById(UUID id) {
        deleteById(id);
        LOG.infof("Professional deleted: %s", id);
    }
}