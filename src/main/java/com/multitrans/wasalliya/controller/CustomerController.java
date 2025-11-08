package com.multitrans.wasalliya.controller;

import com.multitrans.wasalliya.model.dto.CustomerDTO;
import com.multitrans.wasalliya.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerSer;

    @Autowired
    public CustomerController(CustomerService customerSer) {
        this.customerSer = customerSer;
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> save(@Valid @RequestBody CustomerDTO dto){
        return ResponseEntity.ok(customerSer.createCustomer(dto));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> index(){
        return ResponseEntity.ok(customerSer.getAllCustomers());
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@PathVariable Long id){
        return ResponseEntity.ok(customerSer.deleteCustomer(id));
    }

    @PutMapping
    public ResponseEntity<CustomerDTO> update(@PathVariable Long id, @RequestBody CustomerDTO dto){
        return ResponseEntity.ok(customerSer.updateCustomerByID(id,dto));
    }



}
