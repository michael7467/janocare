package com.janocare.auth.api.requests.profile;

import java.util.UUID;

public class UpdateProfileRequest {

    public String firstName;

    public String lastName;

    public String profilePic;

    public String gender;

    public UUID countryId;

    public UUID stateId;

    public UUID cityId;
}