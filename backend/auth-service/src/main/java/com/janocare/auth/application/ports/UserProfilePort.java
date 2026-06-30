package com.janocare.auth.application.ports;

import com.janocare.auth.domain.entities.UserProfile;

import java.util.Optional;
import java.util.UUID;

public interface UserProfilePort {

    UserProfile save(UserProfile profile);

    Optional<UserProfile> findByUserId(UUID userId);
}