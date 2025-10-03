package com.ftn.sbnz.service.services;

import com.ftn.sbnz.model.dtos.ItineraryItem;
import com.ftn.sbnz.model.dtos.TravelPreferences;
import com.ftn.sbnz.model.dtos.travelPlan.TravelPlanResponse;

import java.util.List;

public interface ITravelPlanService {
    TravelPlanResponse generatePlan(TravelPreferences preferences);
}