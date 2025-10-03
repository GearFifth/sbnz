package com.ftn.sbnz.service.controllers;

import com.ftn.sbnz.model.dtos.location.CreateLocationRequest;
import com.ftn.sbnz.model.dtos.location.UpdateLocationRequest;
import com.ftn.sbnz.model.models.Location;
import com.ftn.sbnz.service.services.ILocationService;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

    private final ILocationService locationService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Location> create(@RequestBody CreateLocationRequest request) {
        Location createdLocation = locationService.create(request);
        return new ResponseEntity<>(createdLocation, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Location>> getAll() {
        return ResponseEntity.ok(locationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(locationService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Location> update(@PathVariable UUID id, @RequestBody UpdateLocationRequest request) {
        Location updatedLocation = locationService.update(id, request);
        return ResponseEntity.ok(updatedLocation);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        locationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}