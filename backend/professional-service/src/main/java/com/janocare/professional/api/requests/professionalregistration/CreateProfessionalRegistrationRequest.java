package com.janocare.professional.api.requests.professionalregistration;

import java.time.LocalDate;
import java.util.UUID;

public class CreateProfessionalRegistrationRequest {
    public UUID professionalId;
    public String registrationName;
    public LocalDate registrationDate;
    public String certificatePhoto;
}