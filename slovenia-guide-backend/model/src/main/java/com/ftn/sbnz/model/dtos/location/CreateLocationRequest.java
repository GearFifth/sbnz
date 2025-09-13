package com.ftn.sbnz.model.dtos.location;

import com.ftn.sbnz.model.enums.FitnessLevel;
import com.ftn.sbnz.model.enums.PublicTransportAccessibility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateLocationRequest {
    private String name;
    private String type;
    private String region;
    private double ticketPrice;
    private int visitTimeMinutes;
    private FitnessLevel requiredFitness;
    private PublicTransportAccessibility publicTransportAccessibility;
    private String requiredEquipment;
    private String description;
    private List<String> tags;
    private boolean seasonal;
    private int openingMonth;
    private int closingMonth;
}
