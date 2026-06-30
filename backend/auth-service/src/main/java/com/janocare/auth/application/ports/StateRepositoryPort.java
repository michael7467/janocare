package com.janocare.auth.application.ports;

import com.janocare.auth.domain.entities.State;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StateRepositoryPort {

    State save(State state);

    Optional<State> findDomainById(UUID id);

    List<State> findAllStates();

    List<State> findByCountryId(UUID countryId);

    void deleteStateById(UUID id);
}