package com.janocare.professional.domain.entities;

import java.time.LocalDate;
import java.util.UUID;

public class ProfessionalQualification {

    private UUID id;

    private UUID professionalId;

    private String qualificationName;

    private String institutionName;

    private LocalDate procurementYear;

    protected ProfessionalQualification() {}

    public static ProfessionalQualification create(
            UUID professionalId,
            String qualificationName,
            String institutionName,
            LocalDate procurementYear
    ) {

        ProfessionalQualification qualification = new ProfessionalQualification();

        qualification.id = UUID.randomUUID();
        qualification.professionalId = professionalId;
        qualification.qualificationName = qualificationName;
        qualification.institutionName = institutionName;
        qualification.procurementYear = procurementYear;

        return qualification;
    }

    public static ProfessionalQualification restore(
            UUID id,
            UUID professionalId,
            String qualificationName,
            String institutionName,
            LocalDate procurementYear
    ) {

        ProfessionalQualification qualification = new ProfessionalQualification();

        qualification.id = id;
        qualification.professionalId = professionalId;
        qualification.qualificationName = qualificationName;
        qualification.institutionName = institutionName;
        qualification.procurementYear = procurementYear;

        return qualification;
    }
    public void update(
            String qualificationName,
            String institutionName,
            LocalDate procurementYear
    ) {
        this.qualificationName = qualificationName;
        this.institutionName = institutionName;
        this.procurementYear = procurementYear;
    }
    public UUID getId() {
        return id;
    }

    public UUID getProfessionalId() {
        return professionalId;
    }

    public String getQualificationName() {
        return qualificationName;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public LocalDate getProcurementYear() {
        return procurementYear;
    }
}