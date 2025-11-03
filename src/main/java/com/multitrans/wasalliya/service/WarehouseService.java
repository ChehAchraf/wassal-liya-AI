package com.multitrans.wasalliya.service;


import com.multitrans.wasalliya.helper.LoggingService;
import com.multitrans.wasalliya.model.dto.WarehouseDTO;
import com.multitrans.wasalliya.model.mapper.WarehouseMapper;
import com.multitrans.wasalliya.model.Warehouse;
import com.multitrans.wasalliya.repository.WarehouseRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepo;
    private final WarehouseMapper wMapper;
    private final LoggingService logger;

    @Autowired
    public WarehouseService(WarehouseRepository warehouserepository, WarehouseMapper warehousemapper, LoggingService logger) {
        this.warehouseRepo = warehouserepository;
        this.wMapper = warehousemapper;
        this.logger = logger;
    }

    @Transactional
    public WarehouseDTO createWarehouse(WarehouseDTO dto) {
        logger.logInfo("Attempting to create a new warehouse...");
        Warehouse warehouseToSave = wMapper.toEntity(dto);
        Warehouse savedWarehouse = warehouseRepo.save(warehouseToSave);
        logger.logInfo("Warehouse created successfully with ID: " + savedWarehouse.getId());
        return wMapper.toDTO(savedWarehouse);
    }

    @Transactional
    public WarehouseDTO findWarehouseById(Long id) {
        logger.logInfo("Attempt to find a ware house");
        Warehouse foundedWarehouse = warehouseRepo.findById(id).orElseThrow(NoSuchElementException::new);
        logger.logInfo("Warehouse founded with id : " + foundedWarehouse.getId());
        return wMapper.toDTO(foundedWarehouse);
    }

    @Transactional
    public String deleteWarehouseById(Long id) {
        logger.logInfo("Attempt to delete warehouse by id...");
        Warehouse warehouseToDelete = warehouseRepo.findById(id).orElseThrow(NoSuchElementException::new);
        warehouseRepo.deleteById(id);
        logger.logInfo("Warehouse with id " + warehouseToDelete.getId() +" Deleted" );
        return "Ware house with id " + id + " Has been deleted Seccussfully";
    }

    @Transactional
    public WarehouseDTO updateWarehouseById(Long id, WarehouseDTO dto) {
        logger.logInfo("Attempt to update warehouse");
        Warehouse warehouseToEdit = warehouseRepo.findById(id).orElseThrow(NoSuchElementException::new);
        wMapper.updateFromDTO(dto, warehouseToEdit);
        logger.logInfo("Warehouse has been updated , id : " + warehouseToEdit.getId());
        return wMapper.toDTO(warehouseRepo.save(warehouseToEdit));
    }

}
