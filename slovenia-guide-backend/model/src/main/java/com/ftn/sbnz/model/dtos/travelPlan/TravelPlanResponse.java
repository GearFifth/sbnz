package com.ftn.sbnz.model.dtos.travelPlan;

import com.ftn.sbnz.model.dtos.Alert;
import com.ftn.sbnz.model.dtos.ItineraryItem;
import lombok.Data;

import java.util.List;

@Data
public class TravelPlanResponse {
    private List<ItineraryItem> itinerary;
    private List<Alert> alerts;

    public TravelPlanResponse(List<ItineraryItem> itinerary, List<Alert> alerts) {
        this.itinerary = itinerary;
        this.alerts = alerts;
    }
}
