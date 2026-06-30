package com.janocare.auth.infrastructure.persistence.mappers;

import com.janocare.auth.domain.entities.City;
import com.janocare.auth.infrastructure.persistence.entities.CityJpaEntity;

public class CityPersistenceMapper {

    private CityPersistenceMapper() {}

    public static CityJpaEntity toJpaEntity(City city) {
        CityJpaEntity entity = new CityJpaEntity();

        entity.id = city.getId();
        entity.countryId = city.getCountryId();
        entity.stateId = city.getStateId();
        entity.cityName = city.getCityName();
        entity.active = city.isActive();
        entity.createdAt = city.getCreatedAt();
        entity.updatedAt = city.getUpdatedAt();

        return entity;
    }

    public static City toDomain(CityJpaEntity entity) {
        return City.restore(
                entity.id,
                entity.countryId,
                entity.stateId,
                entity.cityName,
                entity.active,
                entity.createdAt,
                entity.updatedAt
        );
    }
}