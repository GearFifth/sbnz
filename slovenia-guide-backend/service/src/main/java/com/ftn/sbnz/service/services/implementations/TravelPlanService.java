package com.ftn.sbnz.service.services.implementations;

import com.ftn.sbnz.model.dtos.Alert;
import com.ftn.sbnz.model.dtos.ItineraryItem;
import com.ftn.sbnz.model.dtos.Recommendation;
import com.ftn.sbnz.model.dtos.TravelPreferences;
import com.ftn.sbnz.model.dtos.travelPlan.TravelPlanResponse;
import com.ftn.sbnz.model.models.Location;
import com.ftn.sbnz.service.repositories.ILocationRepository;
import com.ftn.sbnz.service.repositories.IRouteRepository;
import com.ftn.sbnz.service.services.ITravelPlanService;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TravelPlanService implements ITravelPlanService {
    private final KieContainer kieContainer;
    private final ILocationRepository locationRepository;

    private final IRouteRepository routeRepository; // Dodajemo repozitorijum za rute

    @Override
    public TravelPlanResponse generatePlan(TravelPreferences preferences) {
        // === FAZA 1: Filtriranje i Bodovanje (ostaje isto) ===
        KieSession scoringSession = kieContainer.newKieSession();
        scoringSession.getAgenda().getAgendaGroup("scoring").setFocus();
        scoringSession.insert(preferences);
        locationRepository.findAll().forEach(scoringSession::insert);
        scoringSession.fireAllRules();

        List<Recommendation> recommendations = new ArrayList<>();
        QueryResults scoringResults = scoringSession.getQueryResults("getRecommendations");
        for (QueryResultsRow row : scoringResults) {
            recommendations.add((Recommendation) row.get("$rec"));
        }
        scoringSession.dispose();

        List<Location> topLocations = recommendations.stream()
                .sorted(Comparator.comparing(Recommendation::getScore).reversed())
                .limit(preferences.getNumberOfDays() * 2)
                .map(Recommendation::getLocation)
                .collect(Collectors.toList());

        // === FAZA 2: Sklapanje Plana i Upozorenja ===
        KieSession session = kieContainer.newKieSession(); // Sada imamo samo jednu sesiju za fazu 2

        // --- Pod-faza 2a: Sklapanje Plana ---
        session.getAgenda().getAgendaGroup("itinerary").setFocus();
        session.insert(preferences);
        topLocations.forEach(session::insert);
        routeRepository.findAll().forEach(session::insert);
        List<ItineraryItem> itinerary = new ArrayList<>();
        session.setGlobal("itinerary", itinerary);
        session.fireAllRules(); // Izvršavamo SAMO pravila iz "itinerary" grupe

        // --- Pod-faza 2b: Generisanje Upozorenja ---
        // Sada kada je 'itinerary' lista popunjena, ubacujemo njene stavke kao nove činjenice
        itinerary.forEach(session::insert);

        session.getAgenda().getAgendaGroup("alerts").setFocus(); // Sada se fokusiramo na "alerts" grupu
        List<Alert> alerts = new ArrayList<>();
        session.setGlobal("alerts", alerts);
        java.util.Set<String> uniqueAlerts = new java.util.HashSet<>();
        session.setGlobal("uniqueAlerts", uniqueAlerts);

        session.fireAllRules(); // Izvršavamo SAMO pravila iz "alerts" grupe
        session.dispose(); // Uništi sesiju tek na kraju

        itinerary.sort(Comparator.comparing(ItineraryItem::getDay));
        return new TravelPlanResponse(itinerary, alerts);
    }
}
