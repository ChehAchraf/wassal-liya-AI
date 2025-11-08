package com.multitrans.wasalliya.service;

import com.multitrans.wasalliya.model.Customer;
import com.multitrans.wasalliya.model.dto.CustomerDTO;
import com.multitrans.wasalliya.model.mapper.CustomerMapper;
import com.multitrans.wasalliya.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepo;
    private final CustomerMapper cMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepo, CustomerMapper cMapper) {
        this.customerRepo = customerRepo;
        this.cMapper = cMapper;
    }

    public CustomerDTO createCustomer(CustomerDTO dto){
        Customer customerToSave = cMapper.toEntity(dto);
        Customer savedCostomer = customerRepo.save(customerToSave);
        return cMapper.toDTO(savedCostomer);
    }

    public List<CustomerDTO> getAllCustomers(){
        List<Customer> allCustomers = customerRepo.findAll();
        return allCustomers.stream().map(cMapper::toDTO).collect(Collectors.toList());
    }
}
