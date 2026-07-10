package com.janocare.auth.api.requests.location;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class CreateStateRequest {

    @NotNull(message = "Country is required")
    public UUID countryId;

    @NotBlank(message = "State name is required")
    public String stateName;

    public Boolean active;
}