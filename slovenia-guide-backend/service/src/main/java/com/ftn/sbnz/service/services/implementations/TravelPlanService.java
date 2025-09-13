package com.ftn.sbnz.service.services.implementations;

import com.ftn.sbnz.model.dtos.Alert;
import com.ftn.sbnz.model.dtos.ItineraryItem;
import com.ftn.sbnz.model.dtos.Recommendation;
import com.ftn.sbnz.model.dtos.TravelPreferences;
import com.ftn.sbnz.model.dtos.travelPlan.TravelPlanResponse;
import com.ftn.sbnz.model.models.Location;
import com.ftn.sbnz.service.repositories.ILocationRepository;
import com.ftn.sbnz.service.repositories.IRouteRepository;
import com.ftn.sbnz.service.repositories.IRuleParameterRepository;
import com.ftn.sbnz.service.services.ITravelPlanService;
import lombok.RequiredArgsConstructor;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TravelPlanService implements ITravelPlanService {

    private final KieContainer kieContainer;
    private final ILocationRepository locationRepository;
    private final IRouteRepository routeRepository;
    private final IRuleParameterRepository ruleParameterRepository;

    @Override
    public TravelPlanResponse generatePlan(TravelPreferences preferences) {
        // === PRIPREMA: Dinamičko generisanje DRL-a i kreiranje nove Kie Baze ===
        InputStream template = TravelPlanService.class.getResourceAsStream("/templates/budget-classification-template.drt");

        List<Map<String, Object>> data = new ArrayList<>();
        ruleParameterRepository.findById("BUDGET_LIMIT_LOW").ifPresent(param -> {
            Map<String, Object> ruleData = new HashMap<>();
            ruleData.put("budgetCategory", "LOW");
            ruleData.put("priceLimit", param.getParamValue());
            data.add(ruleData);
        });
        ruleParameterRepository.findById("BUDGET_LIMIT_MEDIUM").ifPresent(param -> {
            Map<String, Object> ruleData = new HashMap<>();
            ruleData.put("budgetCategory", "MEDIUM");
            ruleData.put("priceLimit", param.getParamValue());
            data.add(ruleData);
        });

        ObjectDataCompiler compiler = new ObjectDataCompiler();
        String generatedDrl = compiler.compile(data, template);

        // Koristimo KieFileSystem da programski dodamo naša nova pravila
        KieServices ks = KieServices.Factory.get();
        KieFileSystem kfs = ks.newKieFileSystem();
        kfs.write("src/main/resources/rules/generated/budget-rules.drl", generatedDrl);

        // Izgradimo novi builder i kreiramo novi, privremeni KieContainer
        // koji sadrži SVA pravila - i stara iz KJAR-a i nova iz templejta
        ks.newKieBuilder(kfs).buildAll();
        KieContainer dynamicKieContainer = ks.newKieContainer(kieContainer.getReleaseId());


        // === FAZA 1: Filtriranje i Bodovanje ===
        // Kreiramo sesiju iz NOVOG, dinamičkog KieContainer-a
        KieSession scoringSession = dynamicKieContainer.newKieSession();
        scoringSession.getAgenda().getAgendaGroup("scoring").setFocus();
        scoringSession.insert(preferences);
        locationRepository.findAll().forEach(scoringSession::insert);
        ruleParameterRepository.findAll().forEach(scoringSession::insert);
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
        KieSession itinerarySession = dynamicKieContainer.newKieSession();

        // --- Pod-faza 2a: Sklapanje Plana ---
        itinerarySession.getAgenda().getAgendaGroup("itinerary").setFocus();
        itinerarySession.insert(preferences);
        topLocations.forEach(itinerarySession::insert);
        routeRepository.findAll().forEach(itinerarySession::insert);
        List<ItineraryItem> itinerary = new ArrayList<>();
        itinerarySession.setGlobal("itinerary", itinerary);
        itinerarySession.fireAllRules();

        // --- Pod-faza 2b: Generisanje Upozorenja ---
        itinerary.forEach(itinerarySession::insert);
        itinerarySession.getAgenda().getAgendaGroup("alerts").setFocus();
        List<Alert> alerts = new ArrayList<>();
        itinerarySession.setGlobal("alerts", alerts);
        Set<String> uniqueAlerts = new HashSet<>();
        itinerarySession.setGlobal("uniqueAlerts", uniqueAlerts);
        itinerarySession.fireAllRules();

        itinerarySession.dispose();

        itinerary.sort(Comparator.comparing(ItineraryItem::getDay));
        return new TravelPlanResponse(itinerary, alerts);
    }
}