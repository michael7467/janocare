package com.janocare.professional.infrastructure.persistence.entities;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(
        name = "professional_sub_specializations",
        indexes = {
                @Index(name = "idx_professional_sub_specializations_professional_id", columnList = "professional_id"),
                @Index(name = "idx_professional_sub_specializations_sub_specialization_id", columnList = "sub_specialization_id")
        }
)
public class ProfessionalSubSpecializationJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    public UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "professional_id", referencedColumnName = "id", nullable = false)
    public ProfessionalJpaEntity professional;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sub_specialization_id", referencedColumnName = "id", nullable = false)
    public SubSpecializationJpaEntity subSpecialization;
}