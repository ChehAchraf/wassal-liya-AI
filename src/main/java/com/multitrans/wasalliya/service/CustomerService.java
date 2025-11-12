package com.multitrans.wasalliya.service;

import com.multitrans.wasalliya.model.Customer;
import com.multitrans.wasalliya.model.dto.CustomerDTO;
import com.multitrans.wasalliya.model.mapper.CustomerMapper;
import com.multitrans.wasalliya.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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

    public String deleteCustomer(Long id){
        Customer customerToDelete = customerRepo.findById(id).orElseThrow(NoSuchElementException::new);
        customerRepo.deleteById(id);
        return "The customer with id"+ id+" has been deleted";
    }

    public CustomerDTO updateCustomerByID(Long id, CustomerDTO dto){
        Customer customerToEdit = customerRepo.findById(id).orElseThrow(NoSuchElementException::new);
        cMapper.updateEntityFromDTO(dto,customerToEdit);
        Customer savedCustomer = customerRepo.save(customerToEdit);
        return cMapper.toDTO(savedCustomer);
    }

    public Page<CustomerDTO> getCustomerPaginated(int page, int size, String nameFilter){

        Pageable pageRequest = PageRequest.of(page,size);

        Page<Customer> customerPage;
        if(nameFilter != null && !nameFilter.isEmpty()){
            customerPage = customerRepo.findByNameContaining(nameFilter,pageRequest);
        }else{
            customerPage = customerRepo.findAll(pageRequest);
        }

        Page<CustomerDTO> dtoPage = customerPage.map(cMapper::toDTO);
        return dtoPage;
    }

}
