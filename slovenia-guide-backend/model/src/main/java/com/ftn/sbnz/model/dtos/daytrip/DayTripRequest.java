package com.ftn.sbnz.model.dtos.daytrip;

import lombok.Data;
import java.util.UUID;

@Data
public class DayTripRequest {
    private UUID baseLocationId; // ID lokacije iz koje tražimo izlete
    private int maxTravelTime;   // Maksimalno vreme putovanja u jednom smeru (u minutima)
}