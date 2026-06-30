package com.janocare.professional.domain.entities;

import com.janocare.professional.domain.enums.DaysOfWeek;

import java.util.UUID;

public class ProfessionalInfo {

    private UUID id;
    private UUID professionalId;

    private String institutionName;

    private String officeNumber;

    private DaysOfWeek daysOfWeek;

    private String startTime;

    private String endTime;

    private boolean available;

    protected ProfessionalInfo() {}

    public static ProfessionalInfo create(
            UUID professionalId,
            String institutionName,
            String officeNumber,
            DaysOfWeek daysOfWeek,
            String startTime,
            String endTime,
            boolean available
    ) {

        ProfessionalInfo info = new ProfessionalInfo();

        info.id = UUID.randomUUID();
        info.professionalId = professionalId;
        info.institutionName = institutionName;
        info.officeNumber = officeNumber;
        info.daysOfWeek = daysOfWeek;
        info.startTime = startTime;
        info.endTime = endTime;
        info.available = available;

        return info;
    }

    public static ProfessionalInfo restore(
            UUID id,
            UUID professionalId,
            String institutionName,
            String officeNumber,
            DaysOfWeek daysOfWeek,
            String startTime,
            String endTime,
            boolean available
    ) {

        ProfessionalInfo info = new ProfessionalInfo();

        info.id = id;
        info.professionalId = professionalId;
        info.institutionName = institutionName;
        info.officeNumber = officeNumber;
        info.daysOfWeek = daysOfWeek;
        info.startTime = startTime;
        info.endTime = endTime;
        info.available = available;

        return info;
    }
    public void update(
            String institutionName,
            String officeNumber,
            DaysOfWeek daysOfWeek,
            String startTime,
            String endTime,
            boolean available
    ) {
        this.institutionName = institutionName;
        this.officeNumber = officeNumber;
        this.daysOfWeek = daysOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.available = available;
    }
    public UUID getId() {
        return id;
    }

    public UUID getProfessionalId() {
        return professionalId;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public DaysOfWeek getDaysOfWeek() {
        return daysOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public boolean isAvailable() {
        return available;
    }

}