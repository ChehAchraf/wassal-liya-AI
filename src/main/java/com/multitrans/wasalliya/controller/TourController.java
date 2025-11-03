package com.multitrans.wasalliya.controller;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collector;

import com.multitrans.wasalliya.enums.VehicalType;
import com.multitrans.wasalliya.model.dto.TourDTO;
import com.multitrans.wasalliya.model.mapper.TourMapper;
import com.multitrans.wasalliya.model.Delivery;
import com.multitrans.wasalliya.model.Warehouse;
import com.multitrans.wasalliya.repository.WarehouseRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.multitrans.wasalliya.model.Tour;
import com.multitrans.wasalliya.service.TourService;

import io.swagger.v3.oas.annotations.Operation;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/tours")
public class TourController {

    private final TourService tourSer;
    private final ObjectMapper objectmap;
    private final TourMapper tourMapper;
    private final WarehouseRepository warehouseRepo;


    public TourController(TourService tourservice, ObjectMapper objectmapper, TourMapper tourMapper, WarehouseRepository warehouseRepo) {
        this.tourSer = tourservice;
        this.objectmap = objectmapper;
        this.tourMapper = tourMapper;
        this.warehouseRepo = warehouseRepo;
    }

    @Operation(summary = "Calculate and get the optimized tour",
            description = "Runs the Optimizer and returns the ordered list of deliveries and total distance.")
    @PostMapping("/optimize")
    public ResponseEntity<?> getOptimizedTour(@RequestParam Long warehouseId, @RequestParam Long vehicaleId) {
        List<Delivery> optimizedList = tourSer.getOptimizedTour(warehouseId, vehicaleId);

        Warehouse warehouse = warehouseRepo.findById(warehouseId)
                .orElseThrow(NoSuchElementException::new);

        double totalDistance = tourSer.getTotalDistance(warehouse, optimizedList);
        Map<String, Object> response = new HashMap<>();
        response.put("algorithmUsed", tourSer.getOptimizerName());
        response.put("totalDistanceKm", totalDistance);
        response.put("deliveryCount", optimizedList.size());
        response.put("orderedDeliveries", optimizedList);

        return ResponseEntity.ok(response);
    }

    // get all tours
    @GetMapping("")
    @Operation(summary = "get all tours", description = "get all the the tours")
    public List<Tour> index() {
        return tourSer.getAllTour();
    }

    // save a tour 
    @PostMapping
    public ResponseEntity<TourDTO> save(@RequestBody TourDTO tourDTO) {
        var savedTour =  tourSer.saveTour(tourDTO);
        return ResponseEntity.ok(savedTour);
    }

    // find tour by id 
    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        Optional<Tour> result = tourSer.findTourById(id);

        return ResponseEntity.ok(result.get());
    }

    // delete a tour
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Optional<Tour> tour = tourSer.findTourById(id);

        tourSer.deleteTour(tour.get());
        return ResponseEntity.ok("tour with id " + id + " has been deleted seccussfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Tour tour) {
        Optional<Tour> updatedTour = tourSer.updateTour(id, tour);

        return ResponseEntity.ok(updatedTour.get());
    }


}