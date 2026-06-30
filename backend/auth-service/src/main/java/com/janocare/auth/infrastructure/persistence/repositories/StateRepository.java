package com.janocare.auth.infrastructure.persistence.repositories;

import com.janocare.auth.application.ports.StateRepositoryPort;
import com.janocare.auth.domain.entities.State;
import com.janocare.auth.infrastructure.persistence.entities.StateJpaEntity;
import com.janocare.auth.infrastructure.persistence.mappers.StatePersistenceMapper;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class StateRepository
        implements PanacheRepositoryBase<StateJpaEntity, UUID>,
        StateRepositoryPort {

    @Override
    @Transactional
    public State save(State state) {
        StateJpaEntity entity =
                StatePersistenceMapper.toJpaEntity(state);

        StateJpaEntity saved =
                getEntityManager().merge(entity);

        return StatePersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<State> findDomainById(UUID id) {
        return findByIdOptional(id)
                .map(StatePersistenceMapper::toDomain);
    }

    @Override
    public List<State> findAllStates() {
        return listAll()
                .stream()
                .map(StatePersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<State> findByCountryId(UUID countryId) {
        return find("countryId", countryId)
                .list()
                .stream()
                .map(StatePersistenceMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteStateById(UUID id) {
        deleteById(id);
    }
}