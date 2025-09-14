package com.ftn.sbnz.service.controllers;

import com.ftn.sbnz.model.events.RoadStatusEvent;
import com.ftn.sbnz.service.services.implementations.RoadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final RoadStatusService roadStatusService;

    @PostMapping("/road-status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> postRoadStatus(@RequestBody RoadStatusEvent event) {
        roadStatusService.addEvent(event);
        return ResponseEntity.ok().build();
    }
}