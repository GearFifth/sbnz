package com.ftn.sbnz.model.dtos;

import com.ftn.sbnz.model.models.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryItem {
    private int day; // Dan putovanja
    private Location location; // Lokacija za taj dan
    private String proposedActivity; // Predložena aktivnost
}