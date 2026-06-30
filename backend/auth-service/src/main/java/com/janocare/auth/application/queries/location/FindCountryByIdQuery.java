package com.janocare.auth.application.queries.location;

import java.util.UUID;

public class FindCountryByIdQuery {

    public UUID countryId;

    public FindCountryByIdQuery() {}

    public FindCountryByIdQuery(UUID countryId) {
        this.countryId = countryId;
    }
}