package com.janocare.professional.infrastructure.persistence.entities;

import com.janocare.professional.domain.enums.DaysOfWeek;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(
        name = "professional_infos",
        indexes = {
                @Index(
                        name = "idx_professional_infos_professional_id",
                        columnList = "professional_id"
                )
        }
)
public class ProfessionalInfoJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    public UUID id;

    @Column(name = "institution_name", nullable = false)
    public String institutionName;

    @Column(name = "office_number", nullable = false)
    public String officeNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "days_of_week", nullable = false)
    public DaysOfWeek daysOfWeek;

    @Column(name = "start_time", nullable = false)
    public String startTime;

    @Column(name = "end_time", nullable = false)
    public String endTime;

    @Column(name = "is_available", nullable = false)
    public boolean available = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(
            name = "professional_id",
            referencedColumnName = "id"
    )
    public ProfessionalJpaEntity professional;
}