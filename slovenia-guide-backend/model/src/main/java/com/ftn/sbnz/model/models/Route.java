package com.ftn.sbnz.model.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "location_a_id", nullable = false)
    private Location locationA;

    @ManyToOne
    @JoinColumn(name = "location_b_id", nullable = false)
    private Location locationB;

    @Column(nullable = false)
    private int travelTimeMinutes;
}