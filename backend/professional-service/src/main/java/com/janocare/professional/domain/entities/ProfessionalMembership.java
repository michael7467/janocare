package com.janocare.professional.domain.entities;

import java.time.LocalDate;
import java.util.UUID;

public class ProfessionalMembership {

    private UUID id;

    private UUID professionalId;

    private String membershipName;

    private String membershipDescription;

    private LocalDate membershipYear;

    protected ProfessionalMembership() {}

    public static ProfessionalMembership create(
            UUID professionalId,
            String membershipName,
            String membershipDescription,
            LocalDate membershipYear
    ) {

        ProfessionalMembership membership = new ProfessionalMembership();

        membership.id = UUID.randomUUID();
        membership.professionalId = professionalId;
        membership.membershipName = membershipName;
        membership.membershipDescription = membershipDescription;
        membership.membershipYear = membershipYear;

        return membership;
    }

    public static ProfessionalMembership restore(
            UUID id,
            UUID professionalId,
            String membershipName,
            String membershipDescription,
            LocalDate membershipYear
    ) {

        ProfessionalMembership membership = new ProfessionalMembership();

        membership.id = id;
        membership.professionalId = professionalId;
        membership.membershipName = membershipName;
        membership.membershipDescription = membershipDescription;
        membership.membershipYear = membershipYear;

        return membership;
    }
    public void update(
            String membershipName,
            String membershipDescription,
            LocalDate membershipYear
    ) {
        this.membershipName = membershipName;
        this.membershipDescription = membershipDescription;
        this.membershipYear = membershipYear;
    }
    public UUID getId() {
        return id;
    }

    public UUID getProfessionalId() {
        return professionalId;
    }

    public String getMembershipName() {
        return membershipName;
    }

    public String getMembershipDescription() {
        return membershipDescription;
    }

    public LocalDate getMembershipYear() {
        return membershipYear;
    }
}