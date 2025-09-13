package com.ftn.sbnz.service.controllers;

import com.ftn.sbnz.model.dtos.ItineraryItem;
import com.ftn.sbnz.model.dtos.TravelPreferences;
import com.ftn.sbnz.model.dtos.travelPlan.TravelPlanResponse;
import com.ftn.sbnz.model.models.Location;
import com.ftn.sbnz.service.services.ITravelPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/travel-plan")
@RequiredArgsConstructor
public class TravelPlanController {

    private final ITravelPlanService travelPlanService;

    @PostMapping("/generate")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<TravelPlanResponse> generatePlan(@RequestBody TravelPreferences preferences) {
        TravelPlanResponse response = travelPlanService.generatePlan(preferences);
        return ResponseEntity.ok(response);
    }
}