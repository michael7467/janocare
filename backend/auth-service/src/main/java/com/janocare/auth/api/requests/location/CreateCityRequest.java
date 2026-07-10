package com.janocare.auth.api.requests.location;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class CreateCityRequest {

    @NotNull(message = "Country is required")
    public UUID countryId;

    @NotNull(message = "State is required")
    public UUID stateId;

    @NotBlank(message = "City name is required")
    public String cityName;

    public Boolean active;
}