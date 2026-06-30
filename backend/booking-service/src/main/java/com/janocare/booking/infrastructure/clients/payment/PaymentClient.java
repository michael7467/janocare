package com.janocare.booking.infrastructure.clients.payment;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.UUID;

@Path("/payments")
@RegisterRestClient(configKey = "payment-service")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface PaymentClient {

    @POST
    PaymentApiResponse<PaymentResponse> createPayment(
            CreatePaymentRequest request
    );

    @GET
    @Path("/booking/{appointmentBookingId}")
    PaymentApiResponse<PaymentResponse> findByAppointmentBookingId(
            @PathParam("appointmentBookingId") UUID appointmentBookingId
    );

    @PUT
    @Path("/{id}/refund")
    PaymentApiResponse<PaymentResponse> refundPayment(
            @PathParam("id") UUID paymentTransactionId
    );
}