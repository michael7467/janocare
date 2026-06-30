package com.janocare.professional.infrastructure.persistence.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "sub_specializations")
public class SubSpecializationJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    public UUID id;

    @Column(name = "specialization_id", nullable = false)
    public UUID specializationId;

    @Column(nullable = false)
    public String name;

    @Column(length = 500)
    public String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "specialization_id",
            referencedColumnName = "id",
            insertable = false,
            updatable = false
    )
    public SpecializationJpaEntity specialization;
}