package com.multitrans.wasalliya.model.dto;

import com.multitrans.wasalliya.enums.DeliveryStatus;
import com.multitrans.wasalliya.model.Tour;
import jakarta.validation.constraints.NotNull;


public record DeliveryDTO(
        Long id,
        @NotNull(message = "Please the address field is required")
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
