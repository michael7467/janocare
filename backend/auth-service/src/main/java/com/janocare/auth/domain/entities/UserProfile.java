package com.janocare.auth.domain.entities;

import java.time.Instant;
import java.util.UUID;

public class UserProfile {

    private UUID id;

    private UUID userId;

    private String firstName;

    private String lastName;

    private String profilePic;

    private String gender;

    private UUID countryId;

    private UUID stateId;

    private UUID cityId;

    private Instant createdAt;

    private Instant updatedAt;

    public UserProfile(UUID userId) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public static UserProfile createFor(User user) {
        return new UserProfile(user.getId());
    }

    public static UserProfile restore(
            UUID id,
            UUID userId,
            String firstName,
            String lastName,
            String profilePic,
            String gender,
            UUID countryId,
            UUID stateId,
            UUID cityId,
            Instant createdAt,
            Instant updatedAt
    ) {
        UserProfile profile = new UserProfile(userId);

        profile.id = id;
        profile.firstName = firstName;
        profile.lastName = lastName;
        profile.profilePic = profilePic;
        profile.gender = gender;
        profile.countryId = countryId;
        profile.stateId = stateId;
        profile.cityId = cityId;
        profile.createdAt = createdAt;
        profile.updatedAt = updatedAt;

        return profile;
    }

    public void updateProfile(
            String firstName,
            String lastName,
            String profilePic,
            String gender,
            UUID countryId,
            UUID stateId,
            UUID cityId
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePic = profilePic;
        this.gender = gender;
        this.countryId = countryId;
        this.stateId = stateId;
        this.cityId = cityId;
        this.updatedAt = Instant.now();
    }

    public void updateProfilePicture(String profilePic) {
        this.profilePic = profilePic;
        this.updatedAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getGender() {
        return gender;
    }

    public UUID getCountryId() {
        return countryId;
    }

    public UUID getStateId() {
        return stateId;
    }

    public UUID getCityId() {
        return cityId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}