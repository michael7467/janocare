package com.janocare.auth.application.queries.user;

import java.util.UUID;

public class FindUserByIdQuery {

    public UUID userId;

    public FindUserByIdQuery() {}

    public FindUserByIdQuery(UUID userId) {
        this.userId = userId;
    }
}