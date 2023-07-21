package com.odoraf.customer;

import org.springframework.data.jpa.repository.JpaRepository;

// Interface to inherit all methods that are used to interact with DB
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Boolean existsByEmail(String email);
}
