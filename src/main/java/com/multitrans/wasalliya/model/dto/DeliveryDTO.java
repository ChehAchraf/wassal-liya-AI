package com.multitrans.wasalliya.model.dto;

import com.multitrans.wasalliya.enums.DeliveryStatus;
import com.multitrans.wasalliya.model.Tour;



public record DeliveryDTO(
        Long id,
        String address,
        Double latitude,
        Double longitude,
        Double weight,
        Double volume,
        String timeWindow,
        DeliveryStatus deliveryStatus,
        Long tourId
) {
}
