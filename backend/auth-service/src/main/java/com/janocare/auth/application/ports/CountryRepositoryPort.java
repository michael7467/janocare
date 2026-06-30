package com.janocare.auth.application.ports;

import com.janocare.auth.domain.entities.Country;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CountryRepositoryPort {

    Country save(Country country);

    Optional<Country> findDomainById(UUID id);

    List<Country> findAllCountries();

    void deleteCountryById(UUID id);
}