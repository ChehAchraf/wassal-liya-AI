package com.multitrans.wasalliya.model.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record WarehouseDTO(
        Long id,
        @NotNull(message = "Latitude must be given")
        Double latitude,
        Double longitude,
        String openingHours,
        List<Long> tourIds
) {
}
