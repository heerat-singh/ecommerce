package com.heeratsingh.ecommerce.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String review;

    @ManyToOne
    @JoinColumn(name="product_id")
    @JsonIgnore
    private Product product;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private LocalDateTime createdAt;

    public Review() {

    }

    public Review(Long id, String review, Product product, User user, LocalDateTime createdAt) {
        super();
        this.id = id;
        this.review = review;
        this.product = product;
        this.user = user;
        this.createdAt = createdAt;
    }

}

