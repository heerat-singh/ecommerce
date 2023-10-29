package com.heeratsingh.ecommerce.service;


import com.heeratsingh.ecommerce.exception.ProductException;
import com.heeratsingh.ecommerce.model.Cart;
import com.heeratsingh.ecommerce.model.CartItem;
import com.heeratsingh.ecommerce.model.User;
import com.heeratsingh.ecommerce.request.AddItemRequest;

public interface CartService {

    public Cart createCart(User user);

    public String addCartItem(Long userId,AddItemRequest req) throws ProductException;

    public Cart findUserCart(Long userId);

}
