package com.multitrans.wasalliya.repository;

import com.multitrans.wasalliya.enums.VehicalType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.multitrans.wasalliya.model.Vehicale;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicaleRepository extends JpaRepository<Vehicale, Long>{
    long countVehicaleByVehicalType(VehicalType type);
}
