package com.ftn.sbnz.service.services;


import com.ftn.sbnz.model.dtos.route.CreateRouteRequest;
import com.ftn.sbnz.model.dtos.route.UpdateRouteRequest;
import com.ftn.sbnz.model.models.Route;

import java.util.List;
import java.util.UUID;

public interface IRouteService {
    Route create(CreateRouteRequest request);
    List<Route> findAll();
    Route findById(UUID id);
    void delete(UUID id);
    Route update(UpdateRouteRequest request);
}