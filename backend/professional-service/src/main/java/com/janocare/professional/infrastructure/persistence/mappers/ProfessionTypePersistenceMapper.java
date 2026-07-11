package com.janocare.professional.infrastructure.persistence.mappers;

import com.janocare.professional.domain.entities.ProfessionType;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionTypeJpaEntity;

public class ProfessionTypePersistenceMapper {

    private ProfessionTypePersistenceMapper() {}

    // ── Domain → JPA entity ──────────────────────────────────
    // Called before save() — converts domain object to JPA entity
    // Domain entity has zero JPA annotations — Data Mapper pattern
    public static ProfessionTypeJpaEntity toJpaEntity(
            ProfessionType domain) {

        ProfessionTypeJpaEntity entity =
                new ProfessionTypeJpaEntity();

        entity.id          = domain.getId();
        entity.name        = domain.getName();
        entity.description = domain.getDescription();
        entity.slotInterval = domain.getSlotInterval(); 
        entity.active      = domain.isActive();          
        entity.createdAt   = domain.getCreatedAt();
        entity.updatedAt   = domain.getUpdatedAt();

        return entity;
    }

    // ── JPA entity → Domain ──────────────────────────────────
    // Called after load() — reconstructs domain object
    // Uses restore() factory — bypasses creation invariants
    public static ProfessionType toDomain(
            ProfessionTypeJpaEntity entity) {

        return ProfessionType.restore(
                entity.id,
                entity.name,
                entity.description,
                entity.slotInterval, 
                entity.active,       
                entity.createdAt,
                entity.updatedAt
        );
    }
}