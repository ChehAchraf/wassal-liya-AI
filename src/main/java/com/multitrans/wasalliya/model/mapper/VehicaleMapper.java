package com.multitrans.wasalliya.model.mapper;

import com.multitrans.wasalliya.model.dto.VehicalDTO;
import com.multitrans.wasalliya.model.Tour;
import com.multitrans.wasalliya.model.Vehicale;
import com.multitrans.wasalliya.repository.TourRepository;

import java.util.List;
import java.util.stream.Collectors;

public class VehicaleMapper {

    private final TourRepository tourrepo;

    public VehicaleMapper(TourRepository tourrepository) {
        this.tourrepo = tourrepository;
    }

    //  DTO to Entity
    public Vehicale toEntity(VehicalDTO dto) {
        Vehicale v = new Vehicale();

        v.setId(dto.getId());
        v.setVehicalType(dto.getVehicalType());
        v.setMaxWeight(dto.getMaxWeight());
        v.setMaxVolume(dto.getMaxVolume());
        v.setMaxDeliveries(dto.getMaxDeliveries());

        if (dto.getTourIDs() != null && !dto.getTourIDs().isEmpty()) {
            List<Tour> tours = dto.getTourIDs().stream()
                    .map(id -> tourrepo.findById(id)
                            .orElseThrow(() -> new RuntimeException("Tour not found: " + id)))
                    .collect(Collectors.toList());
            v.setTours(tours);
        }

        return v;
    }


    public VehicalDTO toDTO(Vehicale v) {
        VehicalDTO dto = new VehicalDTO();

        dto.setId(v.getId());
        dto.setVehicalType(v.getVehicalType());
        dto.setMaxWeight(v.getMaxWeight());
        dto.setMaxVolume(v.getMaxVolume());
        dto.setMaxDeliveries(v.getMaxDeliveries());

        if (v.getTours() != null && !v.getTours().isEmpty()) {
            List<Long> toursId = v.getTours().stream()
                    .map(Tour::getId)
                    .collect(Collectors.toList());
            dto.setTourIDs(toursId);
        }

        return dto;
    }

    public void updateFromDto(VehicalDTO dto, Vehicale entityToUpdate){
        entityToUpdate.setVehicalType(dto.getVehicalType());
        entityToUpdate.setMaxWeight(dto.getMaxWeight());
        entityToUpdate.setMaxVolume(dto.getMaxVolume());
        entityToUpdate.setMaxDeliveries(dto.getMaxDeliveries());
        if(dto.getId() != null){
            List<Tour> tours = dto.getTourIDs().stream()
                    .map(id -> tourrepo.findById(id).orElseThrow(()->new RuntimeException("we couldn't find your tour")))
                    .collect(Collectors.toList());
            entityToUpdate.setTours(tours);
        }
    }
}
