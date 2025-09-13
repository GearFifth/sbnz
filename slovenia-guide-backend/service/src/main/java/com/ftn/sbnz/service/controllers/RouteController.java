package com.ftn.sbnz.service.controllers;

import com.ftn.sbnz.model.dtos.route.CreateRouteRequest;
import com.ftn.sbnz.model.models.Route;
import com.ftn.sbnz.service.services.IRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {

    private final IRouteService routeService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Route> create(@RequestBody CreateRouteRequest request) {
        Route createdRoute = routeService.create(request);
        return new ResponseEntity<>(createdRoute, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Route>> getAll() {
        return ResponseEntity.ok(routeService.findAll());
    }
}