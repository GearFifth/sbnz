package com.ftn.sbnz.service.controllers;

import com.ftn.sbnz.model.dtos.daytrip.DayTripRequest;
import com.ftn.sbnz.model.dtos.daytrip.DayTripSuggestion;
import com.ftn.sbnz.model.dtos.daytrip.ValidateDayTripRequest;
import com.ftn.sbnz.service.services.implementations.DayTripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/day-trips")
@RequiredArgsConstructor
public class DayTripController {

    private final DayTripService dayTripService;

    @PostMapping("/find")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<DayTripSuggestion>> findPossibleDayTrips(@RequestBody DayTripRequest request) {
        return ResponseEntity.ok(dayTripService.findDayTrips(request));
    }

    @PostMapping("/validate")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Boolean> validatePossibleDayTrip(@RequestBody ValidateDayTripRequest request) {
        return ResponseEntity.ok(dayTripService.validateDayTrip(request));
    }
}