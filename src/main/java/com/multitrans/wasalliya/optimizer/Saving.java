package com.multitrans.wasalliya.optimizer;

import com.multitrans.wasalliya.model.Delivery;

public record Saving (
        Delivery deliveryA,
        Delivery deliveryB,
        double savingAmount
)implements Comparable<Saving> {
    @Override
    public int compareTo(Saving other) {
        return Double.compare(other.savingAmount, this.savingAmount);
    }
}
