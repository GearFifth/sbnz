package com.ftn.sbnz.service.services.implementations;

import com.ftn.sbnz.model.dtos.daytrip.DayTripRequest;
import com.ftn.sbnz.model.dtos.daytrip.DayTripSuggestion;
import com.ftn.sbnz.model.dtos.daytrip.ValidateDayTripRequest;
import com.ftn.sbnz.model.facts.DayTripCandidate;
import com.ftn.sbnz.model.models.Location;
import com.ftn.sbnz.service.exceptions.EntityNotFoundException;
import com.ftn.sbnz.service.repositories.ILocationRepository;
import com.ftn.sbnz.service.repositories.IRouteRepository;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DayTripService {

    private final KieContainer kieContainer;
    private final ILocationRepository locationRepository;
    private final IRouteRepository routeRepository;
    private final ModelMapper modelMapper;

    public List<DayTripSuggestion> findDayTrips(DayTripRequest request) {
        KieSession kieSession = kieContainer.newKieSession();
        try {
            locationRepository.findAll().forEach(kieSession::insert);
            routeRepository.findAll().forEach(kieSession::insert);
            kieSession.insert(request);

            kieSession.fireAllRules();

            QueryResults results = kieSession.getQueryResults("getTripCandidates");
            List<DayTripSuggestion> suggestions = new ArrayList<>();
            for (QueryResultsRow row : results) {
                DayTripCandidate candidate = (DayTripCandidate) row.get("$candidate");
                suggestions.add(modelMapper.map(candidate, DayTripSuggestion.class));
            }
            return suggestions;
        } finally {
            kieSession.dispose();
        }
    }

    public boolean validateDayTrip(ValidateDayTripRequest request) {
        KieSession kieSession = kieContainer.newKieSession();
        try {
            locationRepository.findAll().forEach(kieSession::insert);
            routeRepository.findAll().forEach(kieSession::insert);

            Location start = locationRepository.findById(request.getBaseLocationId())
                    .orElseThrow(() -> new EntityNotFoundException("Base location not found."));
            Location destination = locationRepository.findById(request.getDestinationLocationId())
                    .orElseThrow(() -> new EntityNotFoundException("Destination location not found."));

            QueryResults results = kieSession.getQueryResults(
                    "isReachable",
                    start,
                    destination,
                    request.getMaxTravelTime()
            );

            return results.size() > 0;
        } finally {
            kieSession.dispose();
        }
    }
}
