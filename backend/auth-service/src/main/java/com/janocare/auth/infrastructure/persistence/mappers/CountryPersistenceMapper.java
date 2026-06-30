package com.janocare.auth.infrastructure.persistence.mappers;

import com.janocare.auth.domain.entities.Country;
import com.janocare.auth.infrastructure.persistence.entities.CountryJpaEntity;

public class CountryPersistenceMapper {

    private CountryPersistenceMapper() {}

    public static CountryJpaEntity toJpaEntity(Country country) {
        CountryJpaEntity entity = new CountryJpaEntity();

        entity.id = country.getId();
        entity.countryName = country.getCountryName();
        entity.phonePrefix = country.getPhonePrefix();
        entity.active = country.isActive();
        entity.createdAt = country.getCreatedAt();
        entity.updatedAt = country.getUpdatedAt();

        return entity;
    }

    public static Country toDomain(CountryJpaEntity entity) {
        return Country.restore(
                entity.id,
                entity.countryName,
                entity.phonePrefix,
                entity.active,
                entity.createdAt,
                entity.updatedAt
        );
    }
}