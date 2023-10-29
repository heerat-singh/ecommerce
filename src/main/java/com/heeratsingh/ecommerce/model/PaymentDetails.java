package com.heeratsingh.ecommerce.model;

import com.heeratsingh.ecommerce.user.domain.PaymentMethod;
import com.heeratsingh.ecommerce.user.domain.PaymentStatus;
import lombok.Data;

@Data
public class PaymentDetails {

    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private String paymentId;
    private String razorpayPaymentLinkId;
    private String razorpayPaymentLinkReferenceId;
    private String razorpayPaymentLinkStatus;
    private String razorpayPaymentId​;

    public PaymentDetails() {
        // TODO Auto-generated constructor stub
    }

}

