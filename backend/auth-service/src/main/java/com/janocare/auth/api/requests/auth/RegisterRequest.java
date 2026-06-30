package com.janocare.auth.api.requests.auth;


import com.janocare.auth.domain.enums.UserRole;

import java.util.UUID;

public class RegisterRequest {

    public String username;

    public String email;

    public String phone;

    public String password;

    public String deviceName;

    public String deviceType;

    // =====================================================
    // PROFESSIONAL SUPPORT
    // =====================================================

    public UserRole role;

    public UUID professionTypeId;
}