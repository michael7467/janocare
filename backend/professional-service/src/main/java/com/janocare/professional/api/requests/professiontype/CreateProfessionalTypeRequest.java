package com.janocare.professional.api.requests.professiontype;

public class CreateProfessionalTypeRequest {

    @NotBlank(message = "Name is required")
    public String name;

    public String description;

    @NotNull(message = "Slot interval is required")
    @Min(value = 5, message = "Slot interval must be at least 5 minutes")
    @Max(value = 480, message = "Slot interval cannot exceed 480 minutes")
    public Integer slotInterval;
}