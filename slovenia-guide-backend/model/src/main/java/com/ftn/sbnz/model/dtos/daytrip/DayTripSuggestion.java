package com.ftn.sbnz.model.dtos.daytrip;

import com.ftn.sbnz.model.models.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayTripSuggestion {
    private Location location;
    private int travelTimeFromBase;
}