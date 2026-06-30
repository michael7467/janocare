package com.janocare.auth.api.mappers;

import com.janocare.auth.api.responses.location.CityResponse;
import com.janocare.auth.domain.entities.City;

public class CityApiMapper {

    private CityApiMapper() {}

    public static CityResponse toResponse(City city) {
        if (city == null) {
            return null;
        }

        CityResponse response = new CityResponse();

        response.id = city.getId();
        response.countryId = city.getCountryId();
        response.stateId = city.getStateId();
        response.cityName = city.getCityName();
        response.active = city.isActive();

        return response;
    }
}