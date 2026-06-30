package com.janocare.auth.infrastructure.persistence.repositories;

import com.janocare.auth.application.ports.CityRepositoryPort;
import com.janocare.auth.domain.entities.City;
import com.janocare.auth.infrastructure.persistence.entities.CityJpaEntity;
import com.janocare.auth.infrastructure.persistence.mappers.CityPersistenceMapper;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class CityRepository
        implements PanacheRepositoryBase<CityJpaEntity, UUID>,
        CityRepositoryPort {

    @Override
    @Transactional
    public City save(City city) {
        CityJpaEntity entity =
                CityPersistenceMapper.toJpaEntity(city);

        CityJpaEntity saved =
                getEntityManager().merge(entity);

        return CityPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<City> findDomainById(UUID id) {
        return findByIdOptional(id)
                .map(CityPersistenceMapper::toDomain);
    }

    @Override
    public List<City> findAllCities() {
        return listAll()
                .stream()
                .map(CityPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<City> findByCountryId(UUID countryId) {
        return find("countryId", countryId)
                .list()
                .stream()
                .map(CityPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<City> findByStateId(UUID stateId) {
        return find("stateId", stateId)
                .list()
                .stream()
                .map(CityPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteCityById(UUID id) {
        deleteById(id);
    }
}