package com.janocare.auth.api.mappers;

import com.janocare.auth.api.responses.user.UserResponse;
import com.janocare.auth.domain.entities.User;

public class UserApiMapper {

    private UserApiMapper() {}

    public static UserResponse toResponse(User user) {

        if (user == null) {
            return null;
        }

        UserResponse response = new UserResponse();

        response.id = user.getId().toString();

        response.username = user.getUsername();

        response.email = user.getEmail().getValue();

        response.phone = user.getPhone().getValue();

        response.role = user.getRole().name();

        response.status = user.getStatus().name();

        return response;
    }
}