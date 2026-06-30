package com.janocare.professional.infrastructure.persistence.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "specializations")
public class SpecializationJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    public UUID id;

    @Column(nullable = false, unique = true)
    public String name;

    @Column(length = 500)
    public String description;
}