package com.ftn.sbnz.service.services.implementations;

import com.ftn.sbnz.model.dtos.route.CreateRouteRequest;
import com.ftn.sbnz.model.dtos.route.UpdateRouteRequest;
import com.ftn.sbnz.model.models.Location;
import com.ftn.sbnz.model.models.Route;
import com.ftn.sbnz.service.exceptions.EntityExistsException;
import com.ftn.sbnz.service.exceptions.EntityNotFoundException;
import com.ftn.sbnz.service.repositories.IRouteRepository;
import com.ftn.sbnz.service.services.ILocationService;
import com.ftn.sbnz.service.services.IRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RouteService implements IRouteService {

    private final IRouteRepository routeRepository;
    private final ILocationService locationService;

    @Override
    public Route create(CreateRouteRequest routeDTO) {
        Location locationA = locationService.findById(routeDTO.getLocationAId());
        Location locationB = locationService.findById(routeDTO.getLocationBId());

        if (routeRepository.findByLocationAAndLocationB(locationA, locationB).isPresent() ||
                routeRepository.findByLocationAAndLocationB(locationB, locationA).isPresent()) {
            throw new EntityExistsException("Route between these locations already exists.");
        }

        Route route = new Route(null, locationA, locationB, routeDTO.getTravelTimeMinutes());
        return routeRepository.save(route);
    }

    @Override
    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        findById(id);
        routeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Route update(UpdateRouteRequest request) {
        Route existingRoute = findById(request.getId());

        existingRoute.setTravelTimeMinutes(request.getTravelTimeMinutes());
        return routeRepository.save(existingRoute);
    }

    @Override
    public Route findById(UUID id) {
        return routeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Route with ID " + id + " not found."));
    }
}