package com.janocare.professional.infrastructure.persistence.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(
        name = "professional_specializations",
        indexes = {
                @Index(
                        name = "idx_professional_specializations_professional_id",
                        columnList = "professional_id"
                ),
                @Index(
                        name = "idx_professional_specializations_specialization_id",
                        columnList = "specialization_id"
                )
        }
)
public class ProfessionalSpecializationJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    public UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "professional_id",
            referencedColumnName = "id",
            nullable = false
    )
    public ProfessionalJpaEntity professional;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "specialization_id",
            referencedColumnName = "id",
            nullable = false
    )
    public SpecializationJpaEntity specialization;
}