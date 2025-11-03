package com.multitrans.wasalliya.model.dto;

import java.util.List;

import com.multitrans.wasalliya.enums.VehicalType;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class VehicalDTO {
    private Long id;
    private VehicalType vehicalType;
    private double maxWeight;
    private double maxVolume;
    private int maxDeliveries;
    private List<Long> tourIDs;
}
