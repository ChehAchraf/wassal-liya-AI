package com.multitrans.wasalliya.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name="warehouses")
@Data
public class Warehouse {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="latitude" , nullable=false)
    private double latitude;

    @Column(name="longitude" , nullable=false)
    private double longitude;

    @Column(name="opening_hours" , nullable=false)
    private String openingHours;

    @OneToMany(mappedBy = "warehouse")
    @JsonManagedReference
    private List<Tour> tours;

}
