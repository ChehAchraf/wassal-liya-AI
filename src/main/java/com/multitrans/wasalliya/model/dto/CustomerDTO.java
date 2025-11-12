package com.multitrans.wasalliya.model.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CustomerDTO(
        Long id,
        @NotNull(message = "Customer must have a name!")
        String name,
        @NotNull(message = "Customer must have an address!")
        String address,
        @NotNull(message = "Customer must have a latitude!")
        Double latitude,
        @NotNull(message = "Customer must have a longitude!")
        Double longitude,
        @NotNull(message = "Customer must have a Prefered time slot!")
        String preferredTimeSlot,
        List<Long> deliveriesIds
) {
}
