package com.multitrans.wasalliya.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String preferredTimeSlot;
    @OneToMany(mappedBy = "customer")
    @JsonManagedReference("delivery-customer")
    private List<Delivery> deliveries;
}
