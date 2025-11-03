package com.multitrans.wasalliya.service;


import com.multitrans.wasalliya.model.dto.TourDTO;
import com.multitrans.wasalliya.model.mapper.TourMapper;
import com.multitrans.wasalliya.model.Delivery;
import com.multitrans.wasalliya.model.Tour;
import com.multitrans.wasalliya.model.Vehicale;
import com.multitrans.wasalliya.model.Warehouse;
import com.multitrans.wasalliya.repository.DeliveryRepository;
import com.multitrans.wasalliya.repository.TourRepository;
import com.multitrans.wasalliya.repository.VehicaleRepository;
import com.multitrans.wasalliya.repository.WarehouseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TourServiceTest {

    @Mock
    private TourRepository tourRepo;
    @Mock
    private WarehouseRepository warehouseRepository;
    @Mock
    private VehicaleRepository vehicaleRepository;
    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private TourMapper tMapper;

    @InjectMocks
    private TourService tourSer;

    @Test
    void getOptimizedTour() {
    }

    @Test
    void getAllTour() {
    }

    @Test
    void saveTour(TourDTO dto) {

        TourDTO inputDto = new TourDTO(
                null,
                LocalDate.now(),
                150.5,
                List.of(10L, 11L),
                1L,
                2L
        );

        Vehicale mockVehicale = new Vehicale();
        mockVehicale.setId(1L);

        Warehouse mockWarehouse = new Warehouse();
        mockWarehouse.setId(2L);

        Delivery delivery1 = new Delivery();
        delivery1.setId(10L);
        Delivery delivery2 = new Delivery();
        delivery2.setId(11L);
        List<Delivery> mockDeliveries = List.of(delivery1, delivery2);

        Tour savedTourEntity = new Tour();
        savedTourEntity.setId(99L);
        savedTourEntity.setDate(inputDto.date());
        savedTourEntity.setTotalDistance(inputDto.totalDistance());
        savedTourEntity.setVehicale(mockVehicale);
        savedTourEntity.setWarehouse(mockWarehouse);
        savedTourEntity.setDeliveries(mockDeliveries);

        when(vehicaleRepository.findById(1L)).thenReturn(Optional.of(mockVehicale));
        when(warehouseRepository.findById(2L)).thenReturn(Optional.of(mockWarehouse));
        when(deliveryRepository.findAllById(List.of(10L, 11L))).thenReturn(mockDeliveries);
        when(tourRepo.save(any(Tour.class))).thenReturn(savedTourEntity);
        TourDTO result = tourSer.saveTour(inputDto);
    }

    @Test
    void findTourById_ShoulReturnTour() {
        Tour expectedTour = new Tour();
        expectedTour.setId(1L);

        when(tourRepo.findById(1L)).thenReturn(Optional.of(expectedTour));

        Optional<Tour> actualTour = tourSer.findTourById(1L);

        assertNotNull(actualTour);
        assertEquals(1L,actualTour.get().getId());
    }

    @Test
    void deleteTour() {
    }

    @Test
    void updateTour() {
    }

    @Test
    void getTotalDistance() {
    }

    @Test
    void getOptimizerName() {
    }
}