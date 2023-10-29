package com.heeratsingh.ecommerce.repository;

import com.heeratsingh.ecommerce.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
