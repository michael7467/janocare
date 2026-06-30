package com.janocare.auth.infrastructure.persistence.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "countries")
public class CountryJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    public UUID id;

    @Column(name = "country_name", nullable = false)
    public String countryName;

    @Column(name = "phone_prefix", nullable = false)
    public String phonePrefix;

    @Column(name = "is_active", nullable = false)
    public boolean active;

    @Column(name = "created_at", nullable = false)
    public Instant createdAt;

    @Column(name = "updated_at")
    public Instant updatedAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }

        if (updatedAt == null) {
            updatedAt = Instant.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }
}