package com.multitrans.wasalliya.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.multitrans.wasalliya.enums.DeliveryStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name="deliveries")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Delivery {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;


    @Column(name = "address", nullable = false, length = 255)
    private String address;


    @Column(name = "latitude", nullable = false)
    private double latitude;


    @Column(name = "longitude", nullable = false)
    private double longitude;


    @Column(name = "weight")
    private double weight;


    @Column(name = "volume")
    private double volume;


    @Column(name = "start_time_window")
    private String timeWindow;


    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status", nullable = false)
    private DeliveryStatus deliveryStatus;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tour_id")
    @JsonBackReference
    private Tour tour;

    @ManyToOne
    @JoinColumn(name="customer_id")
    @JsonBackReference("delivery-customer")
    private Customer customer;

}
