package com.janocare.auth.api.responses.user;

import com.janocare.auth.api.responses.profile.ProfileResponse;

public class UserResponse {

    public String id;

    public String username;

    public String email;

    public String phone;

    public String role;

    public String status;

    public ProfileResponse userProfile;
}