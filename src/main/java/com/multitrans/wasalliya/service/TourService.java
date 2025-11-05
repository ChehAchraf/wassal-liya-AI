package com.multitrans.wasalliya.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.multitrans.wasalliya.enums.VehicalType;
import com.multitrans.wasalliya.helper.LoggingService;
import com.multitrans.wasalliya.model.*;
import com.multitrans.wasalliya.model.dto.TourDTO;
import com.multitrans.wasalliya.enums.DeliveryStatus;
import com.multitrans.wasalliya.model.mapper.TourMapper;
import com.multitrans.wasalliya.optimizer.TourOptimizer;
import com.multitrans.wasalliya.repository.*;
import com.multitrans.wasalliya.util.DistanceCalculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class TourService {

    private final TourRepository tourRepo;
    private final TourMapper tourmapper;
    private final TourOptimizer tourOptimizer;
    private final DeliveryRepository deliveryRepo;
    private final VehicaleRepository vehicaleRepo;
    private final WarehouseRepository warehouseRepo;
    private final LoggingService logger;
    private final DeliveryHistoryRepository historyRepo;

    @Autowired
    public TourService(TourRepository tourRepository, TourMapper topurmapper, TourOptimizer tourOptimizer, DeliveryRepository deliveryRepo, VehicaleRepository vehicaleRepo, WarehouseRepository warehouseRepo, LoggingService logger, DeliveryHistoryRepository historyRepo) {
        this.tourRepo = tourRepository;
        this.tourmapper = topurmapper;
        this.tourOptimizer = tourOptimizer;
        this.deliveryRepo = deliveryRepo;
        this.vehicaleRepo = vehicaleRepo;
        this.warehouseRepo = warehouseRepo;
        this.logger = logger;
        this.historyRepo = historyRepo;
    }

    public List<Delivery> getOptimizedTour(Long warehouseId, Long vehicaleId) {
        logger.logInfo("attempt to get the iptimized tour");
        Warehouse warehouse = warehouseRepo.findById(warehouseId)
                .orElseThrow(NoSuchElementException::new);

        Vehicale vehicle = vehicaleRepo.findById(vehicaleId)
                .orElseThrow(NoSuchElementException::new);

        List<Delivery> pendingDeliveries = deliveryRepo.findAllByDeliveryStatus(DeliveryStatus.PENDING);
        List<Delivery> orderedDeliveries = this.tourOptimizer.calculateOptimalTour(warehouse, pendingDeliveries, vehicle);
        return orderedDeliveries;


    }

    @Transactional
    public List<Tour> getAllTour() {
        return tourRepo.findAll();
    }

    @Transactional
    public TourDTO saveTour(TourDTO dto) {
        logger.logInfo("attempt to save tour");
        Tour tourToSave = tourmapper.toEntity(dto);
        Tour savedTour = tourRepo.save(tourToSave);
        logger.logInfo("Tour with id :" + savedTour.getId()+" has been saved" );
        return tourmapper.toDTO(savedTour);
    }

    @Transactional
    public Optional<Tour> findTourById(Long tourId) {
        logger.logInfo("Attempt to find tour by id");
        return tourRepo.findById(tourId);

    }

    @Transactional
    public void deleteTour(Tour tour) {
        logger.logInfo("Attempt to delete a tour");
        tourRepo.delete(tour);
    }

    @Transactional
    public Optional<Tour> updateTour(Long id, Tour newTourData) {
        logger.logInfo("Attempt to update a tour");
        Optional<Tour> existingTour = tourRepo.findById(id);

        if (existingTour.isEmpty()) {
            return Optional.empty();
        }
        logger.logInfo("Checked if tour is already exists");
        Tour tour = existingTour.get();

        tour.setDate(newTourData.getDate());
        tour.setTotalDistance(newTourData.getTotalDistance());
        tour.setVehicale(newTourData.getVehicale());

        tourRepo.save(tour);
        logger.logInfo("tour has been founded and updated and being saved into the db successfully");
        return Optional.of(tour);
    }

    public double getTotalDistance(Warehouse warehouse, List<Delivery> orderedTour) {
        if (orderedTour == null || orderedTour.isEmpty()) {
            return 0.0;
        }
        double totalDistance = 0.0;
        double whLat = warehouse.getLatitude();
        double whLon = warehouse.getLongitude();
        Delivery firstDelivery = orderedTour.get(0);
        totalDistance += DistanceCalculator.calculateDistance(
                whLat, whLon,
                firstDelivery.getLatitude(), firstDelivery.getLongitude()
        );

        for (int i = 0; i < orderedTour.size() - 1; i++) {
            Delivery current = orderedTour.get(i);
            Delivery next = orderedTour.get(i + 1);

            totalDistance += DistanceCalculator.calculateDistance(
                    current.getLatitude(), current.getLongitude(),
                    next.getLatitude(), next.getLongitude()
            );
        }

        Delivery lastDelivery = orderedTour.get(orderedTour.size() - 1);
        totalDistance += DistanceCalculator.calculateDistance(
                lastDelivery.getLatitude(), lastDelivery.getLongitude(),
                whLat, whLon
        );

        return totalDistance;

    }

    public Object getOptimizerName() {
        if (this.tourOptimizer == null) {
            return "No Optimizer Injected";
        }
        return this.tourOptimizer.getClass().getSimpleName();
    }

    @Transactional
    public void updateTourStatus(Long id){
        Tour tour = tourRepo.findById(id).orElseThrow(NoSuchElementException::new);
        tour.getDeliveries().forEach(delivery -> {
            delivery.setDeliveryStatus(DeliveryStatus.DELIVRED);

            DeliveryHistory history = new DeliveryHistory();

            history.setCustomer(delivery.getCustomer());

            history.setDayOfWeek(tour.getDate().getDayOfWeek());

            LocalTime planned = parsePlannedTime(delivery.getTimeWindow());
            LocalTime actual = LocalTime.now();

            history.setPlannedTime(planned);
            history.setActualTime(actual);
            if (planned != null && actual != null) {
                Duration delayDuration = Duration.between(planned, actual);
                history.setDelayInMinutes(delayDuration.toMinutes());
            }
            historyRepo.save(history);
        });
    }

    private LocalTime parsePlannedTime(String timeWindow) {
        if (timeWindow == null || timeWindow.isEmpty()) {
            return null;
        }

        try {
            String startTimeString = timeWindow.split("-")[0].trim();
            return LocalTime.parse(startTimeString);

        } catch (Exception e) {

            System.err.println("Could not parse time window: " + timeWindow);
            return null;
        }
    }



}