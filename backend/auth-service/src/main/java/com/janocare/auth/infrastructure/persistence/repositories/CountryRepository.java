package com.janocare.auth.infrastructure.persistence.repositories;

import com.janocare.auth.application.ports.CountryRepositoryPort;
import com.janocare.auth.domain.entities.Country;
import com.janocare.auth.infrastructure.persistence.entities.CountryJpaEntity;
import com.janocare.auth.infrastructure.persistence.mappers.CountryPersistenceMapper;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class CountryRepository
        implements PanacheRepositoryBase<CountryJpaEntity, UUID>,
        CountryRepositoryPort {

    @Override
    @Transactional
    public Country save(Country country) {
        CountryJpaEntity entity =
                CountryPersistenceMapper.toJpaEntity(country);

        CountryJpaEntity saved =
                getEntityManager().merge(entity);

        return CountryPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Country> findDomainById(UUID id) {
        return findByIdOptional(id)
                .map(CountryPersistenceMapper::toDomain);
    }

    @Override
    public List<Country> findAllCountries() {
        return listAll()
                .stream()
                .map(CountryPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteCountryById(UUID id) {
        deleteById(id);
    }
}