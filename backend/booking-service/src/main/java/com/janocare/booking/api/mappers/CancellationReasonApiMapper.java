package com.janocare.booking.api.mappers;

import com.janocare.booking.api.responses.CancellationReasonResponse;
import com.janocare.booking.domain.entities.CancellationReason;

public class CancellationReasonApiMapper {

    private CancellationReasonApiMapper() {}

    public static CancellationReasonResponse toResponse(CancellationReason reason) {
        CancellationReasonResponse response = new CancellationReasonResponse();

        response.id = reason.getId();
        response.reason = reason.getReason();

        return response;
    }
}