package com.multitrans.wasalliya.optimizer;

import com.multitrans.wasalliya.model.Delivery;
import com.multitrans.wasalliya.model.Vehicale;
import com.multitrans.wasalliya.model.Warehouse;
import com.multitrans.wasalliya.util.DistanceCalculator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
@ConditionalOnProperty(name = "app.optimizer.strategy", havingValue = "clarkeWright")
public class ClarkeWright implements TourOptimizer{
    @Override
    public List<Delivery> calculateOptimalTour(Warehouse warehouse, List<Delivery> pendingDeliveries, Vehicale vehicle) {
        List<Saving> savingsList = new ArrayList<>();

        double whLat = warehouse.getLatitude();
        double whLon = warehouse.getLongitude();

        for (int i = 0; i < pendingDeliveries.size(); i++) {
            Delivery deliveryA = pendingDeliveries.get(i);

            for (int j = i + 1; j < pendingDeliveries.size(); j++) {
                Delivery deliveryB = pendingDeliveries.get(j);

                double dist_WA = DistanceCalculator.calculateDistance(whLat, whLon, deliveryA.getLatitude(), deliveryA.getLongitude());
                double dist_WB = DistanceCalculator.calculateDistance(whLat, whLon, deliveryB.getLatitude(), deliveryB.getLongitude());
                double dist_AB = DistanceCalculator.calculateDistance(deliveryA.getLatitude(), deliveryA.getLongitude(), deliveryB.getLatitude(), deliveryB.getLongitude());

                double savingAmount = (dist_WA + dist_WB) - dist_AB;

                if (savingAmount > 0) {
                    savingsList.add(new Saving(deliveryA, deliveryB, savingAmount));
                }
            }
        }

        Collections.sort(savingsList);

        Map<Delivery, CWRoute> routeMap = new HashMap<>();

        for (Delivery delivery : pendingDeliveries) {
            routeMap.put(delivery, new CWRoute(delivery));
        }

        for (Saving saving : savingsList) {
            Delivery deliveryA = saving.deliveryA();
            Delivery deliveryB = saving.deliveryB();

            CWRoute routeA = routeMap.get(deliveryA);
            CWRoute routeB = routeMap.get(deliveryB);


            if (routeA == routeB) {
                continue;
            }

            if (!routeA.canMergeWith(routeB, vehicle)) {
                continue;
            }

            boolean merged = false;

            if (routeA.getEnd() == deliveryA && routeB.getStart() == deliveryB) {
                routeA.merge(routeB);
                merged = true;
            } else if (routeA.getStart() == deliveryA && routeB.getEnd() == deliveryB) {
                routeB.merge(routeA);
                routeMap.put(deliveryA, routeB);
                routeA = routeB;
                merged = true;
            } else if (routeA.getStart() == deliveryA && routeB.getStart() == deliveryB) {
                routeA.reverse();
                routeA.merge(routeB);
                merged = true;
            } else if (routeA.getEnd() == deliveryA && routeB.getEnd() == deliveryB) {
                routeB.reverse();
                routeA.merge(routeB);
                merged = true;
            }

            if (merged) {
                for (Delivery deliveryInMergedRoute : routeB.getDeliveries()) {
                    routeMap.put(deliveryInMergedRoute, routeA);
                }
            }
        }

        Set<CWRoute> finalRoutes = new HashSet<>(routeMap.values());

        CWRoute bestRoute = finalRoutes.stream()
                .max(Comparator.comparing(CWRoute::getDeliveryCount))
                .orElse(null);

        if (bestRoute != null) {
            return bestRoute.getDeliveries();
        } else {
            return new ArrayList<>();
        }
    }
}
