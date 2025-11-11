package com.multitrans.wasalliya.model.mapper;

import com.multitrans.wasalliya.model.Customer;
import com.multitrans.wasalliya.model.Delivery;
import com.multitrans.wasalliya.model.dto.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CustomerMapper {


    @Mapping(target = "deliveriesIds", source = "deliveries", qualifiedByName = "deliveriesToIds")
    CustomerDTO toDTO(Customer customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deliveries", ignore = true)
    Customer toEntity(CustomerDTO dto);

    @Named("deliveriesToIds")
    default List<Long> mapDeliveriesToIds(List<Delivery> deliveries) {
        if (deliveries == null) {
            return Collections.emptyList();
        }
        return deliveries.stream()
                .map(Delivery::getId)
                .collect(Collectors.toList());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deliveries", ignore = true)
    void updateEntityFromDTO(CustomerDTO dto, @MappingTarget Customer entity);
}