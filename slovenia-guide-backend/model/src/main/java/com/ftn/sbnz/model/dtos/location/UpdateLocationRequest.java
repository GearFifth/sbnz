package com.ftn.sbnz.model.dtos.location;

import com.ftn.sbnz.model.dtos.TagDto;
import com.ftn.sbnz.model.enums.FitnessLevel;
import com.ftn.sbnz.model.enums.PublicTransportAccessibility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLocationRequest {
    private UUID id;
    private String name;
    private String type;
    private String region;
    private double ticketPrice;
    private int visitTimeMinutes;
    private FitnessLevel requiredFitness;
    private PublicTransportAccessibility publicTransportAccessibility;
    private String requiredEquipment;
    private String description;
    private List<TagDto> tags;
    private boolean seasonal;
    private int openingMonth;
    private int closingMonth;
}
