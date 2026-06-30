package com.janocare.auth.domain.entities;

import java.time.Instant;
import java.util.UUID;

public class City {

    private UUID id;

    private UUID countryId;

    private UUID stateId;

    private String cityName;

    private boolean active;

    private Instant createdAt;

    private Instant updatedAt;

    private City() {}

    public static City create(
            UUID countryId,
            UUID stateId,
            String cityName,
            boolean active
    ) {

        City city = new City();

        city.id = UUID.randomUUID();

        city.countryId = countryId;

        city.stateId = stateId;

        city.cityName = cityName;

        city.active = active;

        city.createdAt = Instant.now();

        city.updatedAt = Instant.now();

        return city;
    }

    public static City restore(
            UUID id,
            UUID countryId,
            UUID stateId,
            String cityName,
            boolean active,
            Instant createdAt,
            Instant updatedAt
    ) {

        City city = new City();

        city.id = id;

        city.countryId = countryId;

        city.stateId = stateId;

        city.cityName = cityName;

        city.active = active;

        city.createdAt = createdAt;

        city.updatedAt = updatedAt;

        return city;
    }

    public void update(
            UUID countryId,
            UUID stateId,
            String cityName,
            Boolean active
    ) {

        this.countryId = countryId;

        this.stateId = stateId;

        this.cityName = cityName;

        if (active != null) {
            this.active = active;
        }

        this.updatedAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getCountryId() {
        return countryId;
    }

    public UUID getStateId() {
        return stateId;
    }

    public String getCityName() {
        return cityName;
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