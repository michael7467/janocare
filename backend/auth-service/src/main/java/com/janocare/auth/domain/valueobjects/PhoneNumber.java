package com.janocare.auth.domain.valueobjects;

import com.janocare.auth.domain.exceptions.InvalidPhoneException;

public class PhoneNumber {

    private final String value;

    public PhoneNumber(String value) {
        if (value == null || value.length() < 7) {
            throw new InvalidPhoneException("Invalid phone number: " + value);
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
