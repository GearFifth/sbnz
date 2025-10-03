package com.ftn.sbnz.service.controllers;

import com.ftn.sbnz.model.events.RoadStatusEvent;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    @Qualifier("cepSession")
    private final KieSession cepSession;

    @PostMapping("/road-status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> postRoadStatus(@RequestBody RoadStatusEvent event) {
        event.setTimestamp(new Date());
        cepSession.insert(event);
        cepSession.fireAllRules();
        return ResponseEntity.ok().build();
    }
}