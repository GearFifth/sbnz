package com.ftn.sbnz.service.services.implementations;

import com.ftn.sbnz.model.dtos.ItineraryItem;
import com.ftn.sbnz.model.dtos.travelPlan.TravelPlanResponse;
import com.ftn.sbnz.model.facts.TrendingLocation;
import com.ftn.sbnz.model.models.Location;
import com.ftn.sbnz.service.repositories.ILocationRepository;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CepSimulationRunner implements CommandLineRunner {

    @Qualifier("cepSession")
    private final KieSession cepSession;

    private final ILocationRepository locationRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n--- STARTING CEP TRENDING SIMULATION ---");

        System.out.println("Fetching real locations from database...");
        Optional<Location> bledOptional = locationRepository.findByName("Lake Bled");
        Optional<Location> postojnaOptional = locationRepository.findByName("Postojna Cave");

        if (bledOptional.isEmpty() || postojnaOptional.isEmpty()) {
            System.err.println("[CEP SIM ERROR] Could not find 'Lake Bled' or 'Postojna Cave' in the database.");
            System.err.println("Please add them to your database to run the simulation.");
            System.out.println("--- CEP SIMULATION FAILED ---");
            return;
        }

        Location bled = bledOptional.get();
        Location postojna = postojnaOptional.get();

        System.out.println("Found real Lake Bled with ID: " + bled.getId());
        System.out.println("Found real Postojna Cave with ID: " + postojna.getId());

        System.out.println("Simulating 60 visits for Lake Bled...");
        for (int i = 0; i < 60; i++) {
            ItineraryItem item = new ItineraryItem(1, bled, "Visit Bled");
            TravelPlanResponse response = new TravelPlanResponse(
                    UUID.randomUUID(),
                    Collections.singletonList(item),
                    Collections.emptyList(),
                    "Test Trip"
            );
            cepSession.insert(response);
            cepSession.fireAllRules();
        }

        System.out.println("Simulating 40 visits for Postojna Cave...");
        for (int i = 0; i < 40; i++) {
            ItineraryItem item = new ItineraryItem(1, postojna, "Visit Postojna");
            TravelPlanResponse response = new TravelPlanResponse(
                    UUID.randomUUID(),
                    Collections.singletonList(item),
                    Collections.emptyList(),
                    "Test Trip"
            );
            cepSession.insert(response);
            cepSession.fireAllRules();
        }

        System.out.println("--- SIMULATION FINISHED ---");
        System.out.println("Querying for trending locations...");

        QueryResults results = cepSession.getQueryResults("getTrendingLocations");
        System.out.println("Found " + results.size() + " trending location(s).");

        for (QueryResultsRow row : results) {
            TrendingLocation trending = (TrendingLocation) row.get("$tr");
            System.out.println(
                    "[RESULT] " + trending.getLocationName() +
                            " (ID: " + trending.getLocationId() + ")" +
                            " is trending with " + trending.getVisitCount() + " visits."
            );
        }

        System.out.println("--- CEP SIMULATION COMPLETE ---");
    }
}