package com.janocare.auth.infrastructure.persistence.mappers;

import com.janocare.auth.domain.entities.UserProfile;
import com.janocare.auth.infrastructure.persistence.entities.UserProfileEntity;

public class UserProfileMapper {

    private UserProfileMapper() {}

    public static UserProfileEntity toEntity(UserProfile domain) {

        if (domain == null) {
            return null;
        }

        UserProfileEntity entity = new UserProfileEntity();

        entity.setId(domain.getId());

        entity.setUserId(domain.getUserId());

        entity.setFirstName(domain.getFirstName());

        entity.setLastName(domain.getLastName());

        entity.setProfilePic(domain.getProfilePic());

        entity.setGender(domain.getGender());

        entity.setCountryId(domain.getCountryId());
        entity.setStateId(domain.getStateId());
        entity.setCityId(domain.getCityId());

        // IMPORTANT
        entity.setCreatedAt(domain.getCreatedAt());

        entity.setUpdatedAt(domain.getUpdatedAt());

        return entity;
    }

    public static UserProfile toDomain(UserProfileEntity entity) {
        if (entity == null) {
            return null;
        }

        return UserProfile.restore(
                entity.getId(),
                entity.getUserId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getProfilePic(),
                entity.getGender(),
                entity.getCountryId(),
                entity.getStateId(),
                entity.getCityId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}