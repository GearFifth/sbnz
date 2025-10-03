package com.ftn.sbnz.service.controllers;

import com.ftn.sbnz.model.dtos.Alert;
import com.ftn.sbnz.model.dtos.ItineraryItem;
import com.ftn.sbnz.model.dtos.TravelPreferences;
import com.ftn.sbnz.model.dtos.travelPlan.TravelPlanResponse;
import com.ftn.sbnz.model.models.Location;
import com.ftn.sbnz.service.services.ITravelPlanService;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/travel-plan")
@RequiredArgsConstructor
public class TravelPlanController {

    private final ITravelPlanService travelPlanService;

    @Qualifier("cepSession")
    private final KieSession cepSession;

    @PostMapping("/generate")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<TravelPlanResponse> generatePlan(@RequestBody TravelPreferences preferences) {
        TravelPlanResponse response = travelPlanService.generatePlan(preferences);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{planId}/critical-alerts")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<Alert>> getCriticalAlerts(@PathVariable UUID planId) {
        List<Alert> alerts = new ArrayList<>();
        QueryResults results = cepSession.getQueryResults("getAlertsForPlan", planId);

        for (QueryResultsRow row : results) {
            Alert alert = (Alert) row.get("$alert");
            alerts.add(alert);
            cepSession.delete(row.getFactHandle("$alert"));
        }

        return ResponseEntity.ok(alerts);
    }
}