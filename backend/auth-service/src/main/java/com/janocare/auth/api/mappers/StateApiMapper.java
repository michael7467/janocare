package com.janocare.auth.api.mappers;

import com.janocare.auth.api.responses.location.StateResponse;
import com.janocare.auth.domain.entities.State;

public class StateApiMapper {

    private StateApiMapper() {}

    public static StateResponse toResponse(State state) {
        if (state == null) {
            return null;
        }

        StateResponse response = new StateResponse();

        response.id = state.getId();
        response.countryId = state.getCountryId();
        response.stateName = state.getStateName();
        response.active = state.isActive();

        return response;
    }
}