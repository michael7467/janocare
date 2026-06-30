package com.janocare.payment.application.queries;

import java.util.UUID;

public class FindPaymentByIdQuery {

    public UUID paymentTransactionId;

    public FindPaymentByIdQuery(UUID paymentTransactionId) {
        this.paymentTransactionId = paymentTransactionId;
    }
}