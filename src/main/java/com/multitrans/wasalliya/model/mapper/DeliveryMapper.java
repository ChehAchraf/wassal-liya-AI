package com.multitrans.wasalliya.model.mapper;

import com.multitrans.wasalliya.model.Delivery;
import com.multitrans.wasalliya.model.dto.DeliveryDTO;
import com.multitrans.wasalliya.model.dto.DeliveryResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import com.multitrans.wasalliya.enums.DeliveryStatus;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface DeliveryMapper {


    @Mapping(source = "tour.id", target = "tourId")
    @Mapping(source = "customer.id", target = "customerId")
    DeliveryDTO toDTO(Delivery delivery);

    List<DeliveryDTO> toDTOList(List<Delivery> deliveries);


    @Mapping(source = "deliveryStatus", target = "deliveryStatus", qualifiedByName = "deliveryStatusToString")
    DeliveryResponseDTO toResponseDTO(Delivery delivery);
    

    @Named("deliveryStatusToString")
    default String deliveryStatusToString(DeliveryStatus status) {
        return status != null ? status.name() : null;
    }

    List<DeliveryResponseDTO> toResponseDTOList(List<Delivery> deliveries);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tour", ignore = true)
    @Mapping(target = "customer", ignore = true)
    Delivery toEntity(DeliveryDTO dto);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tour", ignore = true)
    @Mapping(target = "customer", ignore = true)
    void updateFromDTO(DeliveryDTO dto, @MappingTarget Delivery entity);
}