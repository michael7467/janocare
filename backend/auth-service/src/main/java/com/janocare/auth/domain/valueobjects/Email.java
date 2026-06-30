package com.janocare.auth.domain.valueobjects;

public class Email {

    private final String value;

    public Email(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (!value.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }

        this.value = value.trim().toLowerCase();
    }

    public String getValue() {
        return value;
    }
}