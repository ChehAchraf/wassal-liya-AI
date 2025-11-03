package com.multitrans.wasalliya.optimizer;

import com.multitrans.wasalliya.model.Delivery;
import com.multitrans.wasalliya.model.Vehicale;
import com.multitrans.wasalliya.model.Warehouse;
import org.springframework.stereotype.Component;

import java.util.List;


public interface TourOptimizer {
    List<Delivery> calculateOptimalTour(Warehouse warehouse, List<Delivery> pendingDeliveries, Vehicale vehicle);
}
