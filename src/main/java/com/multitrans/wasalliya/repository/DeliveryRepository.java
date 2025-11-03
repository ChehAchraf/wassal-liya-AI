package com.multitrans.wasalliya.repository;

import com.multitrans.wasalliya.enums.DeliveryStatus;
import com.multitrans.wasalliya.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery,Long> {
    List<Delivery> findAllByDeliveryStatus(DeliveryStatus status);

    //
    @Query("SELECT d FROM Delivery d WHERE d.weight > :weight")
    List<Delivery> findallDeliveriesGreatarThan(@Param("weight") Double Weight);

    public List<Delivery> findAllByDeliveryStatusAndWeightIsGreaterThanEqual( DeliveryStatus status, Double weight);

    @Query("SELECT d FROM Delivery d WHERE d.deliveryStatus = : status AND d.weight > :weight")
    public List<Delivery> findByStatsuAndWeight(@Param("status") DeliveryStatus status, @Param("weight") Double weight);

}
