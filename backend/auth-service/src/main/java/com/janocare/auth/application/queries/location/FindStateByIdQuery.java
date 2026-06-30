package com.janocare.auth.application.queries.location;

import java.util.UUID;

public class FindStateByIdQuery {

    public UUID stateId;

    public FindStateByIdQuery() {}

    public FindStateByIdQuery(UUID stateId) {
        this.stateId = stateId;
    }
}