package com.ftn.sbnz.model.dtos.route;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRouteRequest {
    private UUID id;
    private int travelTimeMinutes;
}