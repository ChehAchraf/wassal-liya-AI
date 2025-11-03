package com.multitrans.wasalliya.optimizer;

import com.multitrans.wasalliya.model.Delivery;
import com.multitrans.wasalliya.model.Vehicale;
import lombok.Getter;

import java.util.LinkedList;

@Getter
public class CWRoute {

    private LinkedList<Delivery> deliveries;

    private double currentWeight;
    private double currentVolume;

    public CWRoute(Delivery initialDelivery) {
        this.deliveries = new LinkedList<>();
        this.deliveries.add(initialDelivery);

        this.currentWeight = initialDelivery.getWeight();
        this.currentVolume = initialDelivery.getVolume();
    }

    public Delivery getStart() {
        return this.deliveries.getFirst();
    }

    public Delivery getEnd() {
        return this.deliveries.getLast();
    }

    public int getDeliveryCount() {
        return this.deliveries.size();
    }

    public boolean canMergeWith(CWRoute other, Vehicale vehicle) {
        if (this.currentWeight + other.currentWeight > vehicle.getMaxWeight()) {
            return false;
        }
        if (this.currentVolume + other.currentVolume > vehicle.getMaxVolume()) {
            return false;
        }
        if (this.getDeliveryCount() + other.getDeliveryCount() > vehicle.getMaxDeliveries()) {
            return false;
        }

        return true;
    }

    public void merge(CWRoute other) {
        this.deliveries.addAll(other.deliveries);

        this.currentWeight += other.currentWeight;
        this.currentVolume += other.currentVolume;
    }

    public void reverse() {
        LinkedList<Delivery> reversedList = new LinkedList<>();
        for (Delivery d : this.deliveries) {
            reversedList.addFirst(d);
        }
        this.deliveries = reversedList;
    }

}
