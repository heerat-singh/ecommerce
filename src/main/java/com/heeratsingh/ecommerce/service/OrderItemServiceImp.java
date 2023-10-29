package com.heeratsingh.ecommerce.service;

import org.springframework.stereotype.Service;

import com.heeratsingh.ecommerce.model.OrderItem;
import com.heeratsingh.ecommerce.repository.OrderItemRepository;

@Service
public class OrderItemServiceImp implements OrderItemService {

    private OrderItemRepository orderItemRepository;
    public OrderItemServiceImp(OrderItemRepository orderItemRepository) {
        this.orderItemRepository=orderItemRepository;
    }
    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {

        return orderItemRepository.save(orderItem);
    }

}

