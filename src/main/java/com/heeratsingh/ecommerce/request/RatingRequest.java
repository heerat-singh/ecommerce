package com.heeratsingh.ecommerce.request;

import jdk.jfr.Name;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
public class RatingRequest {
    private Long productId;
    private double rating;
}

