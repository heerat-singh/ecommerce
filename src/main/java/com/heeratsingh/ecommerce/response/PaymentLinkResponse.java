package com.heeratsingh.ecommerce.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
public class PaymentLinkResponse {

    private String payment_link_url;
    private String payment_link_id;

}

