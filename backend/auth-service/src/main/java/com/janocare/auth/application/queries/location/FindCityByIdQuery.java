package com.janocare.auth.application.queries.location;

import java.util.UUID;

public class FindCityByIdQuery {

    public UUID cityId;

    public FindCityByIdQuery() {}

    public FindCityByIdQuery(UUID cityId) {
        this.cityId = cityId;
    }
}