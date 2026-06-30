package com.janocare.professional.infrastructure.persistence.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "professional_achievements",
        indexes = {
                @Index(
                        name = "idx_professional_achievements_professional_id",
                        columnList = "professional_id"
                )
        }
)
public class ProfessionalAchievementJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    public UUID id;

    @Column(name = "award_or_recognition_name", nullable = false)
    public String awardOrRecognitionName;

    @Column(name = "award_description", nullable = false)
    public String awardDescription;

    @Column(name = "award_year", nullable = false)
    public LocalDate awardYear;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "professional_id",
            referencedColumnName = "id",
            nullable = false
    )
    public ProfessionalJpaEntity professional;
}