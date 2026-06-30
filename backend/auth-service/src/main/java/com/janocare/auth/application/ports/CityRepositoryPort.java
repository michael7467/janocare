package com.janocare.auth.application.ports;

import com.janocare.auth.domain.entities.City;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CityRepositoryPort {

    City save(City city);

    Optional<City> findDomainById(UUID id);

    List<City> findAllCities();

    List<City> findByCountryId(UUID countryId);

    List<City> findByStateId(UUID stateId);

    void deleteCityById(UUID id);
}