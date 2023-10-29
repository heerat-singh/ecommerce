package com.heeratsingh.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.heeratsingh.ecommerce.exception.ProductException;
import com.heeratsingh.ecommerce.model.Product;
import com.heeratsingh.ecommerce.model.Review;
import com.heeratsingh.ecommerce.model.User;
import com.heeratsingh.ecommerce.repository.ProductRepository;
import com.heeratsingh.ecommerce.repository.ReviewRepository;
import com.heeratsingh.ecommerce.request.ReviewRequest;

@Service
public class ReviewServiceImp implements ReviewService {

    private ReviewRepository reviewRepository;
    private ProductService productService;
    private ProductRepository productRepository;

    public ReviewServiceImp(ReviewRepository reviewRepository,ProductService productService,ProductRepository productRepository) {
        this.reviewRepository=reviewRepository;
        this.productService=productService;
        this.productRepository=productRepository;
    }

    @Override
    public Review createReview(ReviewRequest req,User user) throws ProductException {
        // TODO Auto-generated method stub
        Product product=productService.findProductById(req.getProductId());
        Review review=new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReview(req.getReview());
        review.setCreatedAt(LocalDateTime.now());

//		product.getReviews().add(review);
        productRepository.save(product);
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReview(Long productId) {

        return reviewRepository.getAllProductsReview(productId);
    }

}
