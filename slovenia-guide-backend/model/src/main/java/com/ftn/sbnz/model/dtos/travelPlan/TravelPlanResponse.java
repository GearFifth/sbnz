package com.ftn.sbnz.model.dtos.travelPlan;

import com.ftn.sbnz.model.dtos.Alert;
import com.ftn.sbnz.model.dtos.ItineraryItem;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class TravelPlanResponse {
    private UUID planId;
    private List<ItineraryItem> itinerary;
    private List<Alert> alerts;

    public TravelPlanResponse(UUID planId, List<ItineraryItem> itinerary, List<Alert> alerts) {
        this.planId = planId;
        this.itinerary = itinerary;
        this.alerts = alerts;
    }
}
