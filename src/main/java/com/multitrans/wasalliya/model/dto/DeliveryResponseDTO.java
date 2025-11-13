package com.multitrans.wasalliya.model.dto;


public record DeliveryResponseDTO(
        Long id,
        String address,
        double latitude,
        double longitude,
        double weight,
        double volume,
        String timeWindow,
        String deliveryStatus,
        CustomerDTO customer
) {}