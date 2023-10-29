package com.heeratsingh.ecommerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heeratsingh.exception.CartItemException;
import com.heeratsingh.exception.UserException;
import com.heeratsingh.model.CartItem;
import com.heeratsingh.model.User;
import com.heeratsingh.response.ApiResponse;
import com.heeratsingh.service.CartItemService;
import com.heeratsingh.service.UserService;

@RestController
@RequestMapping("/api/cart_items")
public class CartItemController {

    private CartItemService cartItemService;
    private UserService userService;

    public CartItemController(CartItemService cartItemService,UserService userService) {
        this.cartItemService=cartItemService;
        this.userService=userService;
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse>deleteCartItemHandler(@PathVariable Long cartItemId, @RequestHeader("Authorization")String jwt) throws CartItemException, UserException{

        User user=userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);

        ApiResponse res=new ApiResponse("Item Remove From Cart",true);

        return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem>updateCartItemHandler(@PathVariable Long cartItemId, @RequestBody CartItem cartItem, @RequestHeader("Authorization")String jwt) throws CartItemException, UserException{

        User user=userService.findUserProfileByJwt(jwt);

        CartItem updatedCartItem =cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);

        //ApiResponse res=new ApiResponse("Item Updated",true);

        return new ResponseEntity<>(updatedCartItem,HttpStatus.ACCEPTED);
    }
}

