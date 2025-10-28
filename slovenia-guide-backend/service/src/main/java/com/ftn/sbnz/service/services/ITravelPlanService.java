package com.ftn.sbnz.service.services;

import com.ftn.sbnz.model.dtos.TravelPreferences;
import com.ftn.sbnz.model.dtos.travelPlan.TravelPlanResponse;
import com.ftn.sbnz.model.facts.TrendingLocation;

import java.util.List;

public interface ITravelPlanService {
    List<TrendingLocation> getTrendingLocations();
    TravelPlanResponse generatePlan(TravelPreferences preferences);
}