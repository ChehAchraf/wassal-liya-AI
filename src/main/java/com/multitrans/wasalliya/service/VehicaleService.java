package com.multitrans.wasalliya.service;

import com.multitrans.wasalliya.enums.VehicalType;
import com.multitrans.wasalliya.helper.LoggingService;
import com.multitrans.wasalliya.model.dto.VehicalDTO;
import com.multitrans.wasalliya.model.mapper.VehicaleMapper;
import com.multitrans.wasalliya.model.Vehicale;
import com.multitrans.wasalliya.repository.VehicaleRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class VehicaleService {

    private final VehicaleRepository vehicaleRepo;
    private final VehicaleMapper vmapper;
    private final LoggingService logger;

    @Autowired
    public VehicaleService(VehicaleRepository vehicaleRepository, @Qualifier("vehicaleMapper") VehicaleMapper vmapper, LoggingService logger){
        this.vehicaleRepo = vehicaleRepository;
        this.vmapper = vmapper;
        this.logger = logger;
    }

    @Transactional
    public VehicalDTO crateVehicale(VehicalDTO dto){
        logger.logInfo("Attempt to create vehical...");
        Vehicale vehicale = vmapper.toEntity(dto);
        Vehicale savedVehicale = vehicaleRepo.save(vehicale);
        logger.logInfo("Vehicale created successfully with id " + savedVehicale.getId());
        return vmapper.toDTO(savedVehicale);
    }

    @Transactional()
    public VehicalDTO findById(Long id){
        logger.logInfo("Attempt to find vehicale by id");
        Vehicale vehicale = vehicaleRepo.findById(id).orElseThrow(()->new RuntimeException("Vehical not found"));
        logger.logInfo("Vehicale with id "+vehicale.getId()+" has been found");
        return vmapper.toDTO(vehicale);
    }

    @Transactional
    public VehicalDTO updateVehicla(Long id, VehicalDTO dto){
        logger.logInfo("Attempt to update Vehicale");
        Vehicale vehicaleToUpdate = vehicaleRepo.findById(id)
                .orElseThrow(NoSuchElementException::new);
        vmapper.updateFromDto(dto,vehicaleToUpdate);
        Vehicale updatedVehicale = vehicaleRepo.save(vehicaleToUpdate);
        logger.logInfo("Vehical with id : "+vehicaleToUpdate.getId()+" has been updated");
        return vmapper.toDTO(updatedVehicale);
    }

    public Long CountVehical(String type){
        logger.logInfo("Attempt to count vehicales");
        VehicalType enumType = VehicalType.valueOf(type.toUpperCase());
        return vehicaleRepo.countVehicaleByVehicalType(enumType);

    }

}
