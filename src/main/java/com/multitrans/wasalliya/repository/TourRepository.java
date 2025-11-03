package com.multitrans.wasalliya.repository;

import com.multitrans.wasalliya.enums.VehicalType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.multitrans.wasalliya.model.Tour;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TourRepository  extends JpaRepository<Tour, Long> {

}
