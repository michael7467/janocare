package com.janocare.professional.api.requests.professional;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class CreateProfessionalRequest {

    @NotNull(message = "User ID is required")
    public UUID userId;

    @NotNull(message = "Profession type ID is required")
    public UUID professionTypeId;
}