package com.multitrans.wasalliya.optimizer;

import com.multitrans.wasalliya.model.Delivery;
import com.multitrans.wasalliya.model.Vehicale;
import com.multitrans.wasalliya.model.Warehouse;
import com.multitrans.wasalliya.util.DistanceCalculator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@ConditionalOnProperty(name = "app.optimizer.strategy", havingValue = "nearestNeighbor")
public class NearestNeighbor implements TourOptimizer{

    @Override
    public List<Delivery> calculateOptimalTour(Warehouse warehouse, List<Delivery> pendingDeliveries, Vehicale vehicle) {
        List<Delivery> orderedTour = new ArrayList<>();

        List<Delivery> remainingDeliveries = new ArrayList<>(pendingDeliveries);

        if (remainingDeliveries.isEmpty()) {
            return orderedTour;
        }
        double currentLat = warehouse.getLatitude();
        double currentLon = warehouse.getLongitude();
        while (!remainingDeliveries.isEmpty()) {

            Delivery closestDelivery = null;
            double minDistance = Double.MAX_VALUE;

            for (Delivery delivery : remainingDeliveries) {
                double distance = DistanceCalculator.calculateDistance(
                        currentLat, currentLon,
                        delivery.getLatitude(), delivery.getLongitude()
                );

                if (distance < minDistance) {
                    minDistance = distance;
                    closestDelivery = delivery;
                }
            }

            if (closestDelivery != null) {
                orderedTour.add(closestDelivery);

                remainingDeliveries.remove(closestDelivery);

                currentLat = closestDelivery.getLatitude();
                currentLon = closestDelivery.getLongitude();
            } else {
                break;
            }
        }

        return orderedTour;
    }

}
