package com.janocare.booking.infrastructure.clients;

import java.util.UUID;

public class ProfessionalResponse {
    public boolean success;
    public ProfessionalData data;

    public static class ProfessionalData {
        public UUID id;
        public UUID userId;
        public String status;
    }
}