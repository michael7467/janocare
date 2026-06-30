package com.janocare.auth.domain.entities;

import java.time.Instant;
import java.util.UUID;

public class Country {

    private UUID id;

    private String countryName;

    private String phonePrefix;

    private boolean active;

    private Instant createdAt;

    private Instant updatedAt;

    private Country() {}

    public static Country create(
            String countryName,
            String phonePrefix,
            boolean active
    ) {

        Country country = new Country();

        country.id = UUID.randomUUID();

        country.countryName = countryName;

        country.phonePrefix = phonePrefix;

        country.active = active;

        country.createdAt = Instant.now();

        country.updatedAt = Instant.now();

        return country;
    }

    public static Country restore(
            UUID id,
            String countryName,
            String phonePrefix,
            boolean active,
            Instant createdAt,
            Instant updatedAt
    ) {

        Country country = new Country();

        country.id = id;

        country.countryName = countryName;

        country.phonePrefix = phonePrefix;

        country.active = active;

        country.createdAt = createdAt;

        country.updatedAt = updatedAt;

        return country;
    }

    public void update(
            String countryName,
            String phonePrefix,
            Boolean active
    ) {

        this.countryName = countryName;

        this.phonePrefix = phonePrefix;

        if (active != null) {
            this.active = active;
        }

        this.updatedAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getPhonePrefix() {
        return phonePrefix;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}