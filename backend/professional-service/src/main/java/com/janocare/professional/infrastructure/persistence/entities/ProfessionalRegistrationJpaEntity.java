package com.janocare.professional.infrastructure.persistence.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "professional_registrations",
        indexes = {
                @Index(
                        name = "idx_professional_registrations_professional_id",
                        columnList = "professional_id"
                )
        }
)
public class ProfessionalRegistrationJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    public UUID id;

    @Column(name = "registration_name", nullable = false)
    public String registrationName;

    @Column(name = "registration_date", nullable = false)
    public LocalDate registrationDate;

    @Column(name = "certificate_photo", nullable = false, length = 1024)
    public String certificatePhoto;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(
            name = "professional_id",
            referencedColumnName = "id"
    )
    public ProfessionalJpaEntity professional;
}