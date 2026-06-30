package com.janocare.professional.application.commands.professionalregistration;

import java.time.LocalDate;
import java.util.UUID;

public class CreateProfessionalRegistrationCommand {
    public UUID professionalId;
    public String registrationName;
    public LocalDate registrationDate;
    public String certificatePhoto;
}