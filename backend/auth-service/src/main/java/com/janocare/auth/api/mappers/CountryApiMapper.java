package com.janocare.auth.api.mappers;

import com.janocare.auth.api.responses.location.CountryResponse;
import com.janocare.auth.domain.entities.Country;

public class CountryApiMapper {

    private CountryApiMapper() {}

    public static CountryResponse toResponse(Country country) {
        if (country == null) {
            return null;
        }

        CountryResponse response = new CountryResponse();

        response.id = country.getId();
        response.countryName = country.getCountryName();
        response.phonePrefix = country.getPhonePrefix();
        response.active = country.isActive();

        return response;
    }
}