package com.multitrans.wasalliya.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "delivery_history")
@Data
@NoArgsConstructor
public class DeliveryHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    @Column(name = "delivery_date", nullable = false)
    private LocalDate dateDeLivraison;

    @Column(name = "day_of_week", nullable = false)
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Column(name = "planned_time")
    private LocalTime plannedTime;

    @Column(name = "actual_time")
    private LocalTime actualTime;

    @Column(name = "delay_in_minutes")
    private Long delayInMinutes;

}
