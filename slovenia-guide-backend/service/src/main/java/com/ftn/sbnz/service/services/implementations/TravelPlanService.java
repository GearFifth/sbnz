package com.ftn.sbnz.service.services.implementations;

import com.ftn.sbnz.model.dtos.Alert;
import com.ftn.sbnz.model.dtos.ItineraryItem;
import com.ftn.sbnz.model.dtos.Recommendation;
import com.ftn.sbnz.model.dtos.travelPlan.TravelPlanResponse;
import com.ftn.sbnz.model.dtos.TravelPreferences;
import com.ftn.sbnz.model.facts.TripClassification;
import com.ftn.sbnz.model.models.Location;
import com.ftn.sbnz.service.repositories.ILocationRepository;
import com.ftn.sbnz.service.repositories.IRouteRepository;
import com.ftn.sbnz.service.repositories.IRuleParameterRepository;
import com.ftn.sbnz.service.services.ITravelPlanService;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TravelPlanService implements ITravelPlanService {

    @Qualifier("cepSession")
    private final KieSession cepSession;
    private final KieContainer kieContainer;
    private final ILocationRepository locationRepository;
    private final IRouteRepository routeRepository;
    private final IRuleParameterRepository ruleParameterRepository;

    private record PlanningResult(List<ItineraryItem> itinerary, List<Alert> alerts, String tripType) {}

    /**
     * Glavna metoda koja orkestrira ceo proces generisanja plana.
     */
    @Override
    public TravelPlanResponse generatePlan(TravelPreferences preferences) {
        List<Recommendation> recommendations = executeScoringPhase(preferences);

        List<Location> topLocations = selectTopLocations(recommendations, preferences.getNumberOfDays());

        PlanningResult result = executePlanningAndClassificationPhase(preferences, topLocations);

        result.itinerary().sort(Comparator.comparing(ItineraryItem::getDay));
        TravelPlanResponse response = new TravelPlanResponse(UUID.randomUUID(), result.itinerary(), result.alerts(), result.tripType());

        cepSession.insert(response);
        cepSession.fireAllRules();

        return response;
    }

    /**
     * Kreira sesiju, ubacuje činjenice, pokreće "scoring" pravila i vraća listu preporuka.
     */
    private List<Recommendation> executeScoringPhase(TravelPreferences preferences) {
        KieSession scoringSession = kieContainer.newKieSession();
        try {
            scoringSession.getAgenda().getAgendaGroup("scoring").setFocus();

            // Provera aktivnih CEP događaja
            QueryResults cepResults = cepSession.getQueryResults("getActiveRoadStatusEvents");
            for (QueryResultsRow row : cepResults) {
                scoringSession.insert(row.get("$event"));
            }

            // Ubacivanje činjenica
            scoringSession.insert(preferences);
            locationRepository.findAll().forEach(scoringSession::insert);
            ruleParameterRepository.findAll().forEach(scoringSession::insert);

            scoringSession.fireAllRules();

            // Prikupljanje rezultata
            List<Recommendation> recommendations = new ArrayList<>();
            QueryResults scoringResults = scoringSession.getQueryResults("getRecommendations");
            for (QueryResultsRow row : scoringResults) {
                recommendations.add((Recommendation) row.get("$rec"));
            }
            return recommendations;
        } finally {
            scoringSession.dispose();
        }
    }

    /**
     * Pomoćna metoda koja sortira preporuke i odabira najboljih N lokacija.
     */
    private List<Location> selectTopLocations(List<Recommendation> recommendations, int numberOfDays) {
        return recommendations.stream()
                .sorted(Comparator.comparing(Recommendation::getScore).reversed())
                .limit(numberOfDays * 2L)
                .map(Recommendation::getLocation)
                .collect(Collectors.toList());
    }

    /**
     * Kreira sesiju, pokreće pravila za sklapanje plana, upozorenja i klasifikaciju.
     * Vraća rezultate upakovane u PlanningResult objekat.
     */
    private PlanningResult executePlanningAndClassificationPhase(TravelPreferences preferences, List<Location> topLocations) {
        KieSession planningSession = kieContainer.newKieSession();
        try {
            // Sklapanje Plana
            List<ItineraryItem> itinerary = new ArrayList<>();
            planningSession.setGlobal("itinerary", itinerary);
            planningSession.getAgenda().getAgendaGroup("itinerary").setFocus();
            planningSession.insert(preferences);
            topLocations.forEach(planningSession::insert);
            routeRepository.findAll().forEach(planningSession::insert);
            planningSession.fireAllRules();

            // Generisanje Upozorenja
            itinerary.forEach(planningSession::insert);
            List<Alert> alerts = new ArrayList<>();
            planningSession.setGlobal("alerts", alerts);
            Set<String> uniqueAlerts = new HashSet<>();
            planningSession.setGlobal("uniqueAlerts", uniqueAlerts);
            planningSession.getAgenda().getAgendaGroup("alerts").setFocus();
            planningSession.fireAllRules();

            // Finalna Klasifikacija
            String tripType = null;
            planningSession.getAgenda().getAgendaGroup("classification").setFocus();
            planningSession.fireAllRules();
            QueryResults classificationResults = planningSession.getQueryResults("getTripClassification");
            if (classificationResults.size() > 0) {
                TripClassification classification = (TripClassification) classificationResults.iterator().next().get("$classification");
                tripType = classification.getType();
            }

            return new PlanningResult(itinerary, alerts, tripType);
        } finally {
            planningSession.dispose();
        }
    }
}