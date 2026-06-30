package com.janocare.professional.domain.entities;

import java.time.LocalDate;
import java.util.UUID;

public class ProfessionalRegistration {

    private UUID id;

    private UUID professionalId;

    private String registrationName;

    private LocalDate registrationDate;

    private String certificatePhoto;

    protected ProfessionalRegistration() {}

    public static ProfessionalRegistration create(
            UUID professionalId,
            String registrationName,
            LocalDate registrationDate,
            String certificatePhoto
    ) {

        ProfessionalRegistration registration = new ProfessionalRegistration();

        registration.id = UUID.randomUUID();
        registration.professionalId = professionalId;
        registration.registrationName = registrationName;
        registration.registrationDate = registrationDate;
        registration.certificatePhoto = certificatePhoto;

        return registration;
    }

    public static ProfessionalRegistration restore(
            UUID id,
            UUID professionalId,
            String registrationName,
            LocalDate registrationDate,
            String certificatePhoto
    ) {

        ProfessionalRegistration registration = new ProfessionalRegistration();

        registration.id = id;
        registration.professionalId = professionalId;
        registration.registrationName = registrationName;
        registration.registrationDate = registrationDate;
        registration.certificatePhoto = certificatePhoto;

        return registration;
    }
    public void update(
            String registrationName,
            LocalDate registrationDate,
            String certificatePhoto
    ) {
        this.registrationName = registrationName;
        this.registrationDate = registrationDate;
        this.certificatePhoto = certificatePhoto;
    }
    public UUID getId() {
        return id;
    }

    public UUID getProfessionalId() {
        return professionalId;
    }

    public String getRegistrationName() {
        return registrationName;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public String getCertificatePhoto() {
        return certificatePhoto;
    }
}