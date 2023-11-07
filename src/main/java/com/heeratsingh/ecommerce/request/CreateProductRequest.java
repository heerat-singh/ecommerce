package com.heeratsingh.ecommerce.request;


import java.util.HashSet;
import java.util.Set;

import com.heeratsingh.ecommerce.model.Category;
import com.heeratsingh.ecommerce.model.Size;
import com.heeratsingh.ecommerce.user.domain.ProductSize;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Data

public class CreateProductRequest {

    private String title;

    private String description;

    private int price;

    private int discountedPrice;

    private int discountPercent;

    private int quantity;

    private String brand;

    private String color;

    private Set<Size> size=new HashSet<>();

    private String imageUrl;

    private String topLavelCategory;
    private String secondLavelCategory;
    private String thirdLavelCategory;

}

