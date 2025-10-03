package com.ftn.sbnz.model.facts;

import com.ftn.sbnz.model.models.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayTripCandidate {
    private Location location;           // Lokacija koja je kandidat
    private Location base;               // Bazna lokacija od koje je krenula potraga
    private int travelTimeFromBase;  // Ukupno vreme putovanja od bazne lokacije
}