package com.janocare.auth.api.requests.location;

import java.util.UUID;

public class UpdateCityRequest {

    public UUID countryId;

    public UUID stateId;

    public String cityName;

    public Boolean active;
}