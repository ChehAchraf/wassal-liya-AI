package com.multitrans.wasalliya.repository;

import com.multitrans.wasalliya.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
