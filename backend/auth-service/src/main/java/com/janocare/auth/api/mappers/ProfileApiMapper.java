package com.janocare.auth.api.mappers;

import com.janocare.auth.api.responses.profile.ProfileResponse;
import com.janocare.auth.domain.entities.UserProfile;

public class ProfileApiMapper {

    private ProfileApiMapper() {}

    public static ProfileResponse toResponse(UserProfile profile) {
        if (profile == null) {
            return null;
        }

        ProfileResponse response = new ProfileResponse();

        response.id = profile.getId();
        response.userId = profile.getUserId();

        response.firstName = profile.getFirstName();
        response.lastName = profile.getLastName();
        response.profilePic = profile.getProfilePic();
        response.gender = profile.getGender();



        return response;
    }
}