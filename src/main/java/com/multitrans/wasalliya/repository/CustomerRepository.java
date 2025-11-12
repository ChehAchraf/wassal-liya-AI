package com.multitrans.wasalliya.repository;

import com.multitrans.wasalliya.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Page<Customer> findByNameContaining(String name, Pageable pageable);

    @Query("SELECT c FROM Customer  c where c.preferredTimeSlot = :slot")
    Page<Customer> findBySlot(String slot, Pageable page);
}
