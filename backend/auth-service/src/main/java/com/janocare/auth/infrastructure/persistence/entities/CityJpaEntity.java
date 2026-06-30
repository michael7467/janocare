package com.janocare.auth.infrastructure.persistence.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "cities")
public class CityJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    public UUID id;

    @Column(name = "country_id", nullable = false)
    public UUID countryId;

    @Column(name = "state_id", nullable = false)
    public UUID stateId;

    @Column(name = "city_name", nullable = false)
    public String cityName;

    @Column(name = "is_active", nullable = false)
    public boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "country_id",
            referencedColumnName = "id",
            insertable = false,
            updatable = false
    )
    public CountryJpaEntity country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "state_id",
            referencedColumnName = "id",
            insertable = false,
            updatable = false
    )
    public StateJpaEntity state;

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