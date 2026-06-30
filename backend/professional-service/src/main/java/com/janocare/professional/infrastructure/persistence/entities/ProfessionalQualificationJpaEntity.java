package com.janocare.professional.infrastructure.persistence.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "professional_qualifications",
        indexes = {
                @Index(
                        name = "idx_professional_qualifications_professional_id",
                        columnList = "professional_id"
                )
        }
)
public class ProfessionalQualificationJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    public UUID id;

    @Column(name = "qualification_name", nullable = false)
    public String qualificationName;

    @Column(name = "institution_name", nullable = false)
    public String institutionName;

    @Column(name = "procurement_year", nullable = false)
    public LocalDate procurementYear;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(
            name = "professional_id",
            referencedColumnName = "id"
    )
    public ProfessionalJpaEntity professional;
}