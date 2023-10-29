package com.heeratsingh.ecommerce.service;

import java.util.List;

import com.heeratsingh.ecommerce.exception.ProductException;
import com.heeratsingh.ecommerce.model.Review;
import com.heeratsingh.ecommerce.model.User;
import com.heeratsingh.ecommerce.request.ReviewRequest;

public interface ReviewService {

    public Review createReview(ReviewRequest req,User user) throws ProductException;

    public List<Review> getAllReview(Long productId);


}
