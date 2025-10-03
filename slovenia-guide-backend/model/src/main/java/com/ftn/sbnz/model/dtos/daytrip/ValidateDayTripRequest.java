package com.ftn.sbnz.model.dtos.daytrip;

import lombok.Data;
import java.util.UUID;

@Data
public class ValidateDayTripRequest {
    private UUID baseLocationId;
    private UUID destinationLocationId;
    private int maxTravelTime;
}