package com.janocare.auth.application.handlers;

import com.janocare.auth.api.mappers.CityApiMapper;
import com.janocare.auth.api.mappers.CountryApiMapper;
import com.janocare.auth.api.mappers.StateApiMapper;

import com.janocare.auth.api.responses.location.CityResponse;
import com.janocare.auth.api.responses.location.CountryResponse;
import com.janocare.auth.api.responses.location.StateResponse;

import com.janocare.auth.application.commands.location.*;

import com.janocare.auth.application.ports.CityRepositoryPort;
import com.janocare.auth.application.ports.CountryRepositoryPort;
import com.janocare.auth.application.ports.StateRepositoryPort;

import com.janocare.auth.application.queries.location.*;

import com.janocare.auth.domain.entities.City;
import com.janocare.auth.domain.entities.Country;
import com.janocare.auth.domain.entities.State;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class LocationHandler {

    @Inject
    CountryRepositoryPort countryRepository;

    @Inject
    StateRepositoryPort stateRepository;

    @Inject
    CityRepositoryPort cityRepository;

    // =====================================================
    // COUNTRY
    // =====================================================

    @Transactional
    public CountryResponse createCountry(
            CreateCountryCommand command
    ) {

//        countryRepository
//                .findByCountryName(command.countryName)
//                .ifPresent(country -> {
//                    throw new BadRequestException(
//                            "Country already exists"
//                    );
//                });

        Country country = Country.create(
                command.countryName,
                command.phonePrefix,
                command.active != null
                        ? command.active
                        : false
        );

        country =
                countryRepository.save(country);

        return CountryApiMapper.toResponse(country);
    }

    @Transactional
    public CountryResponse updateCountry(
            UpdateCountryCommand command
    ) {

        Country country = countryRepository
                .findDomainById(command.countryId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Country not found"
                        )
                );

        country.update(
                command.countryName,
                command.phonePrefix,
                command.active
        );

        country =
                countryRepository.save(country);

        return CountryApiMapper.toResponse(country);
    }

    @Transactional
    public void deleteCountry(
            DeleteCountryCommand command
    ) {

        countryRepository
                .findDomainById(command.countryId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Country not found"
                        )
                );

        countryRepository
                .deleteCountryById(command.countryId);
    }

    public CountryResponse findCountryById(
            FindCountryByIdQuery query
    ) {

        Country country = countryRepository
                .findDomainById(query.countryId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Country not found"
                        )
                );

        return CountryApiMapper.toResponse(country);
    }

    public List<CountryResponse> findAllCountries(
            FindAllCountriesQuery query
    ) {

        return countryRepository
                .findAllCountries()
                .stream()
                .filter(country ->
                        query.active == null ||
                                country.isActive()
                                        == query.active
                )
                .filter(country ->
                        query.search == null ||
                                country.getCountryName()
                                        .toLowerCase()
                                        .contains(
                                                query.search
                                                        .toLowerCase()
                                        )
                )
                .map(CountryApiMapper::toResponse)
                .toList();
    }

    // =====================================================
    // STATE
    // =====================================================

    @Transactional
    public StateResponse createState(
            CreateStateCommand command
    ) {

        countryRepository
                .findDomainById(command.countryId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Country not found"
                        )
                );

//        stateRepository
//                .findByStateName(command.stateName)
//                .ifPresent(state -> {
//                    throw new BadRequestException(
//                            "State already exists"
//                    );
//                });

        State state = State.create(
                command.countryId,
                command.stateName,
                command.active != null
                        ? command.active
                        : false
        );

        state =
                stateRepository.save(state);

        return StateApiMapper.toResponse(state);
    }

    @Transactional
    public StateResponse updateState(
            UpdateStateCommand command
    ) {

        State state = stateRepository
                .findDomainById(command.stateId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "State not found"
                        )
                );

        countryRepository
                .findDomainById(command.countryId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Country not found"
                        )
                );

        state.update(
                command.countryId,
                command.stateName,
                command.active
        );

        state =
                stateRepository.save(state);

        return StateApiMapper.toResponse(state);
    }

    @Transactional
    public void deleteState(
            DeleteStateCommand command
    ) {

        stateRepository
                .findDomainById(command.stateId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "State not found"
                        )
                );

        stateRepository
                .deleteStateById(command.stateId);
    }

    public StateResponse findStateById(
            FindStateByIdQuery query
    ) {

        State state = stateRepository
                .findDomainById(query.stateId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "State not found"
                        )
                );

        return StateApiMapper.toResponse(state);
    }

    public List<StateResponse> findAllStates(
            FindAllStatesQuery query
    ) {

        List<State> states =
                query.countryId != null
                        ? stateRepository.findByCountryId(
                        query.countryId
                )
                        : stateRepository.findAllStates();

        return states.stream()
                .filter(state ->
                        query.active == null ||
                                state.isActive()
                                        == query.active
                )
                .filter(state ->
                        query.search == null ||
                                state.getStateName()
                                        .toLowerCase()
                                        .contains(
                                                query.search
                                                        .toLowerCase()
                                        )
                )
                .map(StateApiMapper::toResponse)
                .toList();
    }

    // =====================================================
    // CITY
    // =====================================================

    @Transactional
    public CityResponse createCity(
            CreateCityCommand command
    ) {

        countryRepository
                .findDomainById(command.countryId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Country not found"
                        )
                );

        stateRepository
                .findDomainById(command.stateId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "State not found"
                        )
                );

//        cityRepository
//                .findByCityName(command.cityName)
//                .ifPresent(city -> {
//                    throw new BadRequestException(
//                            "City already exists"
//                    );
//                });

        City city = City.create(
                command.countryId,
                command.stateId,
                command.cityName,
                command.active != null
                        ? command.active
                        : false
        );

        city =
                cityRepository.save(city);

        return CityApiMapper.toResponse(city);
    }

    @Transactional
    public CityResponse updateCity(
            UpdateCityCommand command
    ) {

        City city = cityRepository
                .findDomainById(command.cityId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "City not found"
                        )
                );

        countryRepository
                .findDomainById(command.countryId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Country not found"
                        )
                );

        stateRepository
                .findDomainById(command.stateId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "State not found"
                        )
                );

        city.update(
                command.countryId,
                command.stateId,
                command.cityName,
                command.active
        );

        city =
                cityRepository.save(city);

        return CityApiMapper.toResponse(city);
    }

    @Transactional
    public void deleteCity(
            DeleteCityCommand command
    ) {

        cityRepository
                .findDomainById(command.cityId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "City not found"
                        )
                );

        cityRepository
                .deleteCityById(command.cityId);
    }

    public CityResponse findCityById(
            FindCityByIdQuery query
    ) {

        City city = cityRepository
                .findDomainById(query.cityId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "City not found"
                        )
                );

        return CityApiMapper.toResponse(city);
    }

    public List<CityResponse> findAllCities(
            FindAllCitiesQuery query
    ) {

        List<City> cities;

        if (query.stateId != null) {

            cities = cityRepository
                    .findByStateId(query.stateId);

        } else if (query.countryId != null) {

            cities = cityRepository
                    .findByCountryId(query.countryId);

        } else {

            cities = cityRepository
                    .findAllCities();
        }

        return cities.stream()
                .filter(city ->
                        query.active == null ||
                                city.isActive()
                                        == query.active
                )
                .filter(city ->
                        query.search == null ||
                                city.getCityName()
                                        .toLowerCase()
                                        .contains(
                                                query.search
                                                        .toLowerCase()
                                        )
                )
                .map(CityApiMapper::toResponse)
                .toList();
    }
}