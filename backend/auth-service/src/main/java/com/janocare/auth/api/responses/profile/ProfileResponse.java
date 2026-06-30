package com.janocare.auth.api.responses.profile;

import com.janocare.auth.api.responses.location.CityResponse;
import com.janocare.auth.api.responses.location.CountryResponse;
import com.janocare.auth.api.responses.location.StateResponse;

import java.util.UUID;

public class ProfileResponse {

    public UUID id;

    public UUID userId;

    public String firstName;

    public String middleName;

    public String lastName;

    public String profilePic;

    public String gender;

    public String nationalId;

    public CountryResponse country;

    public StateResponse state;

    public CityResponse city;
}