package com.multitrans.wasalliya.repository;

import com.multitrans.wasalliya.enums.VehicalType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.multitrans.wasalliya.model.Vehicale;

public interface VehicaleRepository extends JpaRepository<Vehicale, Long>{
    long countVehicaleByVehicalType(VehicalType type);
}
