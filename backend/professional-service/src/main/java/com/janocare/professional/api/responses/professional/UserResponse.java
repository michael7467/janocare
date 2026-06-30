package com.janocare.professional.api.responses.professional;

import java.util.UUID;

public class UserResponse {
    public UUID id;
    public String email;
    public String role;
    public UserProfileResponse userProfile; // ← add this
}