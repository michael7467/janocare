package com.janocare.auth.application.queries.profile;

import java.util.UUID;

public class GetMyProfileQuery {

    public UUID userId;

    public GetMyProfileQuery() {}

    public GetMyProfileQuery(UUID userId) {
        this.userId = userId;
    }
}