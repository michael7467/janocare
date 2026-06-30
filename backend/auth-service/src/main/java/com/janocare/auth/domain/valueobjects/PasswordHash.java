package com.janocare.auth.domain.valueobjects;

import com.janocare.auth.domain.exceptions.InvalidPasswordException;

public class PasswordHash {

    private final String value;

    public PasswordHash(String value) {
        if (value == null || value.length() < 20) {
            throw new InvalidPasswordException("Password hash is invalid");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
