package com.multitrans.wasalliya.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.multitrans.wasalliya.enums.VehicalType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table( name = "vehicales" )
@Getter
@Setter
@NoArgsConstructor
public class Vehicale {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Vehicle type is required")
    @Column( name ="vehical_type" ,  nullable = false )
    private VehicalType vehicalType;

    @Column( name = "max_weight" , nullable= false )
    private double maxWeight;

    @Column( name = "max_volume" , nullable= false )
    private double maxVolume;

    @Column( name = "max_deliveries" , nullable= false )
    private int maxDeliveries;

    @OneToMany(mappedBy = "vehicale", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Tour> tours;
}
