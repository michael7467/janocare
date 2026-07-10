package com.janocare.auth.api.requests.location;

import jakarta.validation.constraints.NotBlank;

public class CreateCountryRequest {

    @NotBlank(message = "Country name is required")
    public String countryName;

    @NotBlank(message = "Phone prefix is required")
    public String phonePrefix;

    public Boolean active;
}