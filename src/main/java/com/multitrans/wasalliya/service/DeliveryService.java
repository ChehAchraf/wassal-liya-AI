package com.multitrans.wasalliya.service;

import com.multitrans.wasalliya.enums.DeliveryStatus;
import com.multitrans.wasalliya.helper.LoggingService;
import com.multitrans.wasalliya.model.Customer;
import com.multitrans.wasalliya.model.Tour;
import com.multitrans.wasalliya.model.dto.DeliveryDTO;
import com.multitrans.wasalliya.model.mapper.DeliveryMapper;
import com.multitrans.wasalliya.model.Delivery;
import com.multitrans.wasalliya.repository.CustomerRepository;
import com.multitrans.wasalliya.repository.DeliveryRepository;
import com.multitrans.wasalliya.repository.TourRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepo;
    private final DeliveryMapper dMapper;
    private final LoggingService logger;
    private final TourRepository tourRepo;
    private final CustomerRepository customerRepo;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepo, DeliveryMapper deliveryMapper, LoggingService logger,
                          TourRepository tourRepo, CustomerRepository customerRepo) {
        this.deliveryRepo = deliveryRepo;
        this.dMapper = deliveryMapper;
        this.logger = logger;
        this.tourRepo = tourRepo;
        this.customerRepo = customerRepo;
    }

    public List<DeliveryDTO> getAllDeliveries() {
        logger.logInfo("Attempt to find all the deliveries in the db ");
        List<Delivery> allDeliveries = deliveryRepo.findAll();
        logger.logInfo("All the deliveries are fetched and stored in a list to pass to the controller");
        return allDeliveries.stream().map(dMapper::toDTO).collect(Collectors.toList());

    }

    public DeliveryDTO createDelivery(DeliveryDTO dto) {
        logger.logInfo("Attempt to create a delivery");
        Delivery deliveryToSave = dMapper.toEntity(dto);
        
        if (dto.tourId() != null) {
            Tour tour = tourRepo.findById(dto.tourId())
                    .orElseThrow(() -> new NoSuchElementException("Tour not found with id: " + dto.tourId()));
            deliveryToSave.setTour(tour);
        }
        if (dto.customerId() != null) {
            Customer customer = customerRepo.findById(dto.customerId())
                    .orElseThrow(() -> new NoSuchElementException("Customer not found with id: " + dto.customerId()));
            deliveryToSave.setCustomer(customer);
        }
        
        Delivery savedDelivery = deliveryRepo.save(deliveryToSave);
        logger.logInfo("Delivery has been saved with id : " + savedDelivery.getId());
        return dMapper.toDTO(savedDelivery);
    }

    public DeliveryDTO findDeliveryById(Long id) {
        logger.logInfo("Attempt fo find delivery");
        Optional<Delivery> foundedDelivery = deliveryRepo.findById(id);
        return foundedDelivery.map(dMapper::toDTO)
                .orElseThrow(() -> new NoSuchElementException("the Delivery with id : " + id + " is not found"));
    }

    public String deleteDeliveryById(Long id) {
        logger.logInfo("Attempt to delete a delivery by id");
        if (!deliveryRepo.existsById(id)) {
            throw new NoSuchElementException("the Delivery with id : " + id + " is not found");
        }

        deliveryRepo.deleteById(id);
        logger.logInfo("Delivery has been deleted");
        return "Delivery Deleted Successfully";
    }

    public DeliveryDTO editDeliveryById(Long id, DeliveryDTO dto) {
        logger.logInfo("Attempt to edit a delivery by id");
        Delivery deliveryToUpdate = deliveryRepo.findById(id).orElseThrow(() -> new NoSuchElementException());
        dMapper.updateFromDTO(dto, deliveryToUpdate);
        
        if (dto.tourId() != null) {
            Tour tour = tourRepo.findById(dto.tourId())
                    .orElseThrow(() -> new NoSuchElementException("Tour not found with id: " + dto.tourId()));
            deliveryToUpdate.setTour(tour);
        }
        if (dto.customerId() != null) {
            Customer customer = customerRepo.findById(dto.customerId())
                    .orElseThrow(() -> new NoSuchElementException("Customer not found with id: " + dto.customerId()));
            deliveryToUpdate.setCustomer(customer);
        }
        
        Delivery savedEntity = deliveryRepo.save(deliveryToUpdate);
        logger.logInfo("Delivery has been updated!");
        return dMapper.toDTO(savedEntity);
    }

    public List<String> getHeavyDeliveryAddresses(Double weight){
        List<Delivery> allDelieveries = deliveryRepo.findallDeliveriesGreatarThan(weight);

        return allDelieveries.stream().map(Delivery::getAddress).toList();

    }

    public List<DeliveryDTO> getByStatusAndWeight(DeliveryStatus status, Double weight){
        List<Delivery> allDeliveries = deliveryRepo.findByStatsuAndWeight(status,weight);
        return allDeliveries.stream().map(d -> dMapper.toDTO(d)).collect(Collectors.toList());
    }


}
