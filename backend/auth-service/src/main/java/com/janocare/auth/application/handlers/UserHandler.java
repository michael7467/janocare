package com.janocare.auth.application.handlers;

import com.janocare.auth.api.responses.profile.ProfileResponse;
import com.janocare.auth.api.responses.user.UserResponse;

import com.janocare.auth.application.ports.UserProfilePort;

import com.janocare.auth.domain.entities.User;
import com.janocare.auth.domain.entities.UserProfile;
import com.janocare.auth.application.ports.UserRepositoryPort;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.UUID;

@ApplicationScoped
public class UserHandler {

    @Inject
    UserRepositoryPort userRepository;

    @Inject
    UserProfilePort userProfilePort;

    public UserResponse findById(UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        UserProfile profile = userProfilePort.findByUserId(id)
                .orElse(null);

        UserResponse response = new UserResponse();

        response.id = user.getId().toString();
        response.username = user.getUsername();

        response.email = user.getEmail() != null
                ? user.getEmail().getValue()
                : null;

        response.phone = user.getPhone() != null
                ? user.getPhone().getValue()
                : null;

        response.role = user.getRole() != null
                ? user.getRole().name()
                : null;

        response.status = user.getStatus() != null
                ? user.getStatus().name()
                : null;

        if (profile != null) {

            ProfileResponse profileResponse = new ProfileResponse();

            profileResponse.id = profile.getId();
            profileResponse.firstName = profile.getFirstName();
            profileResponse.lastName = profile.getLastName();
            profileResponse.gender = profile.getGender();
            profileResponse.profilePic = profile.getProfilePic();

            response.userProfile = profileResponse;
        }

        return response;
    }
}