package com.ftn.sbnz.model.dtos;

import com.ftn.sbnz.model.models.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation {
    private Location location; // Preporučena lokacija
    private double score; // Dodeljeni poeni
    private String reason; // Obrazloženje zašto je preporučena
}