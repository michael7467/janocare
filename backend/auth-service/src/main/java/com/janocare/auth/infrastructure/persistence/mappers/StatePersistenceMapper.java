package com.janocare.auth.infrastructure.persistence.mappers;

import com.janocare.auth.domain.entities.State;
import com.janocare.auth.infrastructure.persistence.entities.StateJpaEntity;

public class StatePersistenceMapper {

    private StatePersistenceMapper() {}

    public static StateJpaEntity toJpaEntity(State state) {
        StateJpaEntity entity = new StateJpaEntity();

        entity.id = state.getId();
        entity.countryId = state.getCountryId();
        entity.stateName = state.getStateName();
        entity.active = state.isActive();
        entity.createdAt = state.getCreatedAt();
        entity.updatedAt = state.getUpdatedAt();

        return entity;
    }

    public static State toDomain(StateJpaEntity entity) {
        return State.restore(
                entity.id,
                entity.countryId,
                entity.stateName,
                entity.active,
                entity.createdAt,
                entity.updatedAt
        );
    }
}