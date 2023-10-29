package com.heeratsingh.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.heeratsingh.ecommerce.exception.ProductException;
import com.heeratsingh.ecommerce.model.Product;
import com.heeratsingh.ecommerce.model.Rating;
import com.heeratsingh.ecommerce.model.User;
import com.heeratsingh.ecommerce.repository.RatingRepository;
import com.heeratsingh.ecommerce.request.RatingRequest;

@Service
public class RatingServiceImp implements RatingService{

    private RatingRepository ratingRepository;
    private ProductService productService;

    public RatingServiceImp(RatingRepository ratingRepository,ProductService productService) {
        this.ratingRepository=ratingRepository;
        this.productService=productService;
    }

    @Override
    public Rating createRating(RatingRequest req,User user) throws ProductException {

        Product product=productService.findProductById(req.getProductId());

        Rating rating=new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setCreatedAt(LocalDateTime.now());

        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductsRating(Long productId) {
        // TODO Auto-generated method stub
        return ratingRepository.getAllProductsRating(productId);
    }



}

