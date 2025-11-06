package com.multitrans.wasalliya.service;

import com.multitrans.wasalliya.model.mapper.CustomerMapper;
import com.multitrans.wasalliya.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepo;
    private final CustomerMapper cMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepo, CustomerMapper cMapper) {
        this.customerRepo = customerRepo;
        this.cMapper = cMapper;
    }
}
