package com.janocare.payment.api.controllers;

import com.janocare.payment.api.mappers.PaymentTransactionApiMapper;
import com.janocare.payment.api.requests.CreatePaymentTransactionRequest;
import com.janocare.payment.api.responses.ApiResponse;

import com.janocare.payment.application.commands.*;
import com.janocare.payment.application.handlers.PaymentHandler;
import com.janocare.payment.application.queries.*;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/payments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PaymentTransactionController {

    @Inject
    PaymentHandler handler;

    @POST
    public Response create(
            CreatePaymentTransactionRequest req
    ) {
        CreatePaymentTransactionCommand command =
                new CreatePaymentTransactionCommand();

        command.appointmentBookingId = req.appointmentBookingId;
        command.patientUserId = req.patientUserId;
        command.professionalId = req.professionalId;
        command.amount = req.amount;
        command.currency = req.currency;
        command.paymentType = req.paymentType;
        command.transactionNote = req.transactionNote;

        return Response.status(Response.Status.CREATED)
                .entity(
                        ApiResponse.success(
                                PaymentTransactionApiMapper.toResponse(
                                        handler.createPayment(command)
                                ),
                                "Payment transaction created successfully"
                        )
                )
                .build();
    }

    @PUT
    @Path("/{id}/confirm")
    public Response confirm(
            @PathParam("id") UUID id
    ) {
        ConfirmPaymentCommand command =
                new ConfirmPaymentCommand();

        command.paymentTransactionId = id;

        return Response.ok(
                ApiResponse.success(
                        PaymentTransactionApiMapper.toResponse(
                                handler.confirmPayment(command)
                        ),
                        "Payment confirmed successfully"
                )
        ).build();
    }

    @PUT
    @Path("/{id}/fail")
    public Response fail(
            @PathParam("id") UUID id
    ) {
        FailPaymentCommand command =
                new FailPaymentCommand();

        command.paymentTransactionId = id;

        return Response.ok(
                ApiResponse.success(
                        PaymentTransactionApiMapper.toResponse(
                                handler.failPayment(command)
                        ),
                        "Payment failed successfully"
                )
        ).build();
    }

    @PUT
    @Path("/{id}/refund")
    public Response refund(
            @PathParam("id") UUID id
    ) {
        RefundPaymentCommand command =
                new RefundPaymentCommand();

        command.paymentTransactionId = id;

        return Response.ok(
                ApiResponse.success(
                        PaymentTransactionApiMapper.toResponse(
                                handler.refundPayment(command)
                        ),
                        "Payment refunded successfully"
                )
        ).build();
    }

    @PUT
    @Path("/{id}/settle")
    public Response settle(
            @PathParam("id") UUID id
    ) {
        SettlePaymentCommand command =
                new SettlePaymentCommand();

        command.paymentTransactionId = id;

        return Response.ok(
                ApiResponse.success(
                        PaymentTransactionApiMapper.toResponse(
                                handler.settlePayment(command)
                        ),
                        "Payment settled successfully"
                )
        ).build();
    }

    @GET
    public Response findAll(
            @QueryParam("patientUserId") UUID patientUserId,
            @QueryParam("professionalId") UUID professionalId
    ) {
        FindAllPaymentsQuery query =
                new FindAllPaymentsQuery();

        query.patientUserId = patientUserId;
        query.professionalId = professionalId;

        return Response.ok(
                ApiResponse.success(
                        handler.findAll(query)
                                .stream()
                                .map(PaymentTransactionApiMapper::toResponse)
                                .toList()
                )
        ).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(
            @PathParam("id") UUID id
    ) {
        return Response.ok(
                ApiResponse.success(
                        PaymentTransactionApiMapper.toResponse(
                                handler.findById(
                                        new FindPaymentByIdQuery(id)
                                )
                        )
                )
        ).build();
    }

    @GET
    @Path("/booking/{appointmentBookingId}")
    public Response findByAppointmentBookingId(
            @PathParam("appointmentBookingId") UUID appointmentBookingId
    ) {
        return Response.ok(
                ApiResponse.success(
                        PaymentTransactionApiMapper.toResponse(
                                handler.findByAppointmentBookingId(
                                        new FindPaymentByAppointmentBookingIdQuery(
                                                appointmentBookingId
                                        )
                                )
                        )
                )
        ).build();
    }
}