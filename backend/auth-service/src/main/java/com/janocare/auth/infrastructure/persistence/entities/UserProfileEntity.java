package com.janocare.auth.infrastructure.persistence.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "user_profiles")
public class UserProfileEntity extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "profile_pic")
    private String profilePic;

    @Column(name = "gender")
    private String gender;

    @Column(name = "country_id")
    private UUID countryId;

    @Column(name = "state_id")
    private UUID stateId;

    @Column(name = "city_id")
    private UUID cityId;

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

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCountryId(UUID countryId) {
        this.countryId = countryId;
    }

    public void setStateId(UUID stateId) {
        this.stateId = stateId;
    }

    public void setCityId(UUID cityId) {
        this.cityId = cityId;
    }
}