package com.heeratsingh.ecommerce.service;

import com.heeratsingh.ecommerce.exception.UserException;
import com.heeratsingh.ecommerce.model.User;

public interface UserService {

    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;

}
