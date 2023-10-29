package com.heeratsingh.ecommerce.service;

import java.util.List;

import com.heeratsingh.ecommerce.exception.ProductException;
import com.heeratsingh.ecommerce.model.Rating;
import com.heeratsingh.ecommerce.model.User;
import com.heeratsingh.ecommerce.request.RatingRequest;

public interface RatingService {

    public Rating createRating(RatingRequest req,User user) throws ProductException;

    public List<Rating> getProductsRating(Long productId);

}

