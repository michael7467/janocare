package com.janocare.professional.infrastructure.persistence.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "professional_memberships",
        indexes = {
                @Index(
                        name = "idx_professional_memberships_professional_id",
                        columnList = "professional_id"
                )
        }
)
public class ProfessionalMembershipJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    public UUID id;

    @Column(name = "membership_name", nullable = false)
    public String membershipName;

    @Column(name = "membership_description", columnDefinition = "TEXT")
    public String membershipDescription;

    @Column(name = "membership_year", nullable = false)
    public LocalDate membershipYear;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(
            name = "professional_id",
            referencedColumnName = "id"
    )
    public ProfessionalJpaEntity professional;
}