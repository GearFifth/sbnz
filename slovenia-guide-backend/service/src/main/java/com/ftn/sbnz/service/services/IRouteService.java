package com.ftn.sbnz.service.services;


import com.ftn.sbnz.model.dtos.route.CreateRouteRequest;
import com.ftn.sbnz.model.models.Route;

import java.util.List;

public interface IRouteService {
    Route create(CreateRouteRequest request);
    List<Route> findAll();
}