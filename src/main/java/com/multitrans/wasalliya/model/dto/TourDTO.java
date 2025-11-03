package com.multitrans.wasalliya.model.dto;

import java.time.LocalDate;
import java.util.List;

public record TourDTO(
        Long id,
        LocalDate date,
        Double totalDistance,
        List<Long> deliveryIds,
        Long vehicaleId,
        Long warehouseId
) {}
