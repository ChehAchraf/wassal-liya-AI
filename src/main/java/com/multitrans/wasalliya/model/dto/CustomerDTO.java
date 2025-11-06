package com.multitrans.wasalliya.model.dto;

import java.util.List;

public record CustomerDTO(
        Long id,
        String name,
        String address,
        Double latitude,
        Double longitude,
        String preferredTimeSlot,
        List<Long> deliveriesIds
) {
}
