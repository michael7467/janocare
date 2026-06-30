package com.janocare.auth.application.ports;


import com.janocare.auth.domain.entities.UserAccess;

public interface UserAccessPort {
    void save(UserAccess access);
}
