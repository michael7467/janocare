package com.janocare.professional.api.requests.professional;

import jakarta.validation.constraints.NotNull;

public class VerifyProfessionalRequest {

    @NotNull(message = "Verified flag is required")
    public Boolean verified;
}