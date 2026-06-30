package com.janocare.professional.infrastructure.persistence.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "professional_experiences",
        indexes = {
                @Index(name = "idx_professional_experiences_professional_id", columnList = "professional_id")
        }
)
public class ProfessionalExperienceJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    public UUID id;

    @Column(columnDefinition = "TEXT", nullable = false)
    public String experience;

    @Column(columnDefinition = "TEXT", nullable = false)
    public String specialization;

    @Column(columnDefinition = "TEXT", nullable = false)
    public String place;

    @Column(name = "start_year", nullable = false)
    public LocalDate startYear;

    @Column(name = "end_year", nullable = false)
    public LocalDate endYear;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "professional_id",
            referencedColumnName = "id",
            nullable = false
    )
    public ProfessionalJpaEntity professional;
}