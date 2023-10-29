package com.heeratsingh.ecommerce.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.heeratsingh.ecommerce.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("Select r from Rating r where r.product.id=:productId")
    public List<Review> getAllProductsReview(@Param("productId") Long productId);
}

