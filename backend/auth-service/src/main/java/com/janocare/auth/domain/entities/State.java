package com.janocare.auth.domain.entities;

import java.time.Instant;
import java.util.UUID;

public class State {

    private UUID id;

    private UUID countryId;

    private String stateName;

    private boolean active;

    private Instant createdAt;

    private Instant updatedAt;

    private State() {}

    public static State create(
            UUID countryId,
            String stateName,
            boolean active
    ) {

        State state = new State();

        state.id = UUID.randomUUID();

        state.countryId = countryId;

        state.stateName = stateName;

        state.active = active;

        state.createdAt = Instant.now();

        state.updatedAt = Instant.now();

        return state;
    }

    public static State restore(
            UUID id,
            UUID countryId,
            String stateName,
            boolean active,
            Instant createdAt,
            Instant updatedAt
    ) {

        State state = new State();

        state.id = id;

        state.countryId = countryId;

        state.stateName = stateName;

        state.active = active;

        state.createdAt = createdAt;

        state.updatedAt = updatedAt;

        return state;
    }

    public void update(
            UUID countryId,
            String stateName,
            Boolean active
    ) {

        this.countryId = countryId;

        this.stateName = stateName;

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

    public String getStateName() {
        return stateName;
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