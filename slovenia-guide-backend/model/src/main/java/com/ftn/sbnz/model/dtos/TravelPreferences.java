package com.ftn.sbnz.model.dtos;

import com.ftn.sbnz.model.enums.Budget;
import com.ftn.sbnz.model.enums.FitnessLevel;
import com.ftn.sbnz.model.enums.Transport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelPreferences {
    private int numberOfDays;
    private Budget budget;
    private Transport transport;
    private FitnessLevel fitnessLevel;
    private List<String> interests;
    private int travelMonth;
}