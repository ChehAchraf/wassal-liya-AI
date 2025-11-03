package com.multitrans.wasalliya.model.mapper;

import com.multitrans.wasalliya.model.dto.TourDTO;
import com.multitrans.wasalliya.model.Delivery;
import com.multitrans.wasalliya.model.Tour;
import com.multitrans.wasalliya.model.Vehicale;
import com.multitrans.wasalliya.model.Warehouse;
import com.multitrans.wasalliya.repository.DeliveryRepository;
import com.multitrans.wasalliya.repository.VehicaleRepository;
import com.multitrans.wasalliya.repository.WarehouseRepository;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Component
public class TourMapper {

    private final VehicaleRepository vehicaleRepo;
    private final DeliveryRepository deliveryRepo;
    private final WarehouseRepository warehouseRepo;

    public TourMapper(VehicaleRepository vehicaleRepo, DeliveryRepository deliveryRepository, WarehouseRepository warehouseRepository) {
        this.vehicaleRepo = vehicaleRepo;
        this.deliveryRepo = deliveryRepository;
        this.warehouseRepo = warehouseRepository;
    }

    public TourDTO toDTO(Tour tour) {
        if (tour == null) {
            return null;
        }

        Long vehicaleId = tour.getVehicale() != null ? tour.getVehicale().getId() : null;
        Long warehouseId = tour.getWarehouse() != null ? tour.getWarehouse().getId() : null;
        
        List<Long> deliveryIds = tour.getDeliveries() != null && !tour.getDeliveries().isEmpty()
                ? tour.getDeliveries().stream()
                        .map(Delivery::getId)
                        .collect(Collectors.toList())
                : Collections.emptyList();

        return new TourDTO(
                tour.getId(),
                tour.getDate(),
                tour.getTotalDistance(),
                deliveryIds,
                vehicaleId,
                warehouseId
        );
    }

    public Tour toEntity(TourDTO dto) {
        if (dto == null) return null;

        Tour tour = new Tour();

        // Map simple fields
        tour.setDate(dto.date());
        tour.setTotalDistance(dto.totalDistance());

        // Fetch and set Vehicale entity
        if (dto.vehicaleId() != null) {
            Vehicale vehicale = vehicaleRepo.findById(dto.vehicaleId())
                    .orElseThrow(() -> new RuntimeException("Vehicale not found with id: " + dto.vehicaleId()));
            tour.setVehicale(vehicale);
        }

        // Fetch and set Warehouse entity
        if (dto.warehouseId() != null) {
            Warehouse warehouse = warehouseRepo.findById(dto.warehouseId())
                    .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + dto.warehouseId()));
            tour.setWarehouse(warehouse);
        }

        // Fetch and set Delivery entities
        if (dto.deliveryIds() != null && !dto.deliveryIds().isEmpty()) {
            List<Delivery> deliveries = deliveryRepo.findAllById(dto.deliveryIds());
            tour.setDeliveries(deliveries);
        }

        return tour;
    }



}