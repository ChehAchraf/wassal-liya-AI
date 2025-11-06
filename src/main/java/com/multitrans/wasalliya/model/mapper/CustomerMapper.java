package com.multitrans.wasalliya.model.mapper;


import com.multitrans.wasalliya.model.Customer;
import com.multitrans.wasalliya.model.Delivery;
import com.multitrans.wasalliya.model.dto.CustomerDTO;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {

    // to dto
    public CustomerDTO toDTO(Customer customer) {
        if (customer == null) {
            return null;
        }

        List<Long> deliveryIds = (customer.getDeliveries() != null)
                ? customer.getDeliveries().stream()
                .map(Delivery::getId)
                .collect(Collectors.toList())
                : Collections.emptyList();

        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getAddress(),
                customer.getLatitude(),
                customer.getLongitude(),
                customer.getPreferredTimeSlot(),
                deliveryIds
        );
    }

    // to entity
    public Customer toEntity(CustomerDTO dto) {
        if (dto == null) {
            return null;
        }

        Customer customer = new Customer();
        customer.setId(dto.id());
        customer.setName(dto.name());
        customer.setAddress(dto.address());
        customer.setLatitude(dto.latitude());
        customer.setLongitude(dto.longitude());
        customer.setPreferredTimeSlot(dto.preferredTimeSlot());


        return customer;
    }

    // updtae entity from dto
    public void updateEntityFromDTO(CustomerDTO dto, Customer entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setName(dto.name());
        entity.setAddress(dto.address());
        entity.setLatitude(dto.latitude());
        entity.setLongitude(dto.longitude());
        entity.setPreferredTimeSlot(dto.preferredTimeSlot());

    }

}
