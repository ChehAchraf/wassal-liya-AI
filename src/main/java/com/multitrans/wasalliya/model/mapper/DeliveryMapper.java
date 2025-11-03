package com.multitrans.wasalliya.model.mapper;

import com.multitrans.wasalliya.model.dto.DeliveryDTO;
import com.multitrans.wasalliya.model.Delivery;
import com.multitrans.wasalliya.model.Tour;
import com.multitrans.wasalliya.repository.TourRepository;

import java.util.NoSuchElementException;

public class DeliveryMapper {
    private final TourRepository tourRepo;

    public DeliveryMapper(TourRepository tourRepository){
        this.tourRepo = tourRepository;
    }

    public Delivery toEntity (DeliveryDTO dto){
        Delivery delivery = new Delivery();

        // start the procces
        delivery.setId(dto.id());
        delivery.setAddress(dto.address());
        delivery.setLatitude(dto.latitude());
        delivery.setLongitude(dto.longitude());
        delivery.setWeight(dto.weight());
        delivery.setVolume(dto.volume());
        delivery.setTimeWindow(dto.timeWindow());
        delivery.setDeliveryStatus(dto.deliveryStatus());
        Tour tour = tourRepo.findById(dto.tourId())
                .orElseThrow(()-> new RuntimeException("Tour with id " + dto.tourId()+ " was not found"));
        delivery.setTour(tour);
        return delivery;
    }

    public DeliveryDTO toDTO(Delivery delivery){
        if (delivery == null) return null;
        return new DeliveryDTO(
                delivery.getId(),
                delivery.getAddress(),
                delivery.getLatitude(),
                delivery.getLongitude(),
                delivery.getWeight(),
                delivery.getVolume(),
                delivery.getTimeWindow(),
                delivery.getDeliveryStatus(),
                delivery.getTour() != null ? delivery.getTour().getId() : null
        );
    }

    public void updateFromDTO(DeliveryDTO dto , Delivery entityToUpdate){
        entityToUpdate.setAddress(dto.address() != null ? dto.address() : entityToUpdate.getAddress());
        entityToUpdate.setLatitude(dto.latitude() != null ? dto.latitude() : entityToUpdate.getLatitude());
        entityToUpdate.setLongitude(dto.longitude() != null ? dto.longitude() : entityToUpdate.getLongitude());
        entityToUpdate.setVolume(dto.volume() != null ? dto.volume() : entityToUpdate.getVolume());
        entityToUpdate.setTimeWindow(dto.timeWindow() != null ? dto.timeWindow() : entityToUpdate.getTimeWindow());
        entityToUpdate.setDeliveryStatus(dto.deliveryStatus() != null ? dto.deliveryStatus() : entityToUpdate.getDeliveryStatus());
        entityToUpdate.setTour(dto.tourId() != null ? tourRepo.findById(dto.tourId()).orElseThrow(()-> new NoSuchElementException()) : entityToUpdate.getTour());
    }

}
