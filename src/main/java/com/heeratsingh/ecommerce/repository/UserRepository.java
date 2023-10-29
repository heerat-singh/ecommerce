package com.heeratsingh.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.heeratsingh.ecommerce.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);

}
