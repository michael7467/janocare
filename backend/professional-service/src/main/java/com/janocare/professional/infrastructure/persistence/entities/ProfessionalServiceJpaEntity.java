package com.janocare.professional.infrastructure.persistence.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(
        name = "professional_services",
        indexes = {
                @Index(
                        name = "idx_professional_services_professional_id",
                        columnList = "professional_id"
                )
        }
)
public class ProfessionalServiceJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    public UUID id;

    @Column(name = "service_name", nullable = false)
    public String serviceName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "professional_id",
            referencedColumnName = "id",
            nullable = false
    )
    public ProfessionalJpaEntity professional;
}