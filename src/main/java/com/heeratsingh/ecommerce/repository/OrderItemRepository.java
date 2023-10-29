package com.heeratsingh.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.heeratsingh.ecommerce.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
