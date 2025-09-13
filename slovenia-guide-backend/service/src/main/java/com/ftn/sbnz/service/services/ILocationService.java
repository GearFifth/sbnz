package com.ftn.sbnz.service.services;

import com.ftn.sbnz.model.dtos.location.CreateLocationRequest;
import com.ftn.sbnz.model.models.Location;

import java.util.List;
import java.util.UUID;

public interface ILocationService {
    Location create(CreateLocationRequest request);
    Location findById(UUID id);
    List<Location> findAll();
}