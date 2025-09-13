package com.ftn.sbnz.service.services.implementations;


import com.ftn.sbnz.model.dtos.*;
import com.ftn.sbnz.model.dtos.travelPlan.TravelPlanResponse;
import com.ftn.sbnz.model.models.Location;
import com.ftn.sbnz.model.enums.Budget;
import com.ftn.sbnz.model.template.BudgetRuleTemplateModel;
import com.ftn.sbnz.service.repositories.ILocationRepository;
import com.ftn.sbnz.service.repositories.IRouteRepository;
import com.ftn.sbnz.service.repositories.IRuleParameterRepository;
import com.ftn.sbnz.service.services.ITravelPlanService;
import lombok.RequiredArgsConstructor;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.kie.internal.utils.KieHelper;

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

        // --- PRIPREMA: Dinamičko generisanje DRL-a iz templejta ---
        InputStream templateStream = TravelPlanService.class.getResourceAsStream("/templates/budget-classification-template.drt");

        // Pripremamo podatke koristeći našu novu model klasu
        List<BudgetRuleTemplateModel> templateData = new ArrayList<>();

        double lowLimit = ruleParameterRepository.findById("BUDGET_LIMIT_LOW").get().getParamValue();
        templateData.add(new BudgetRuleTemplateModel(Budget.LOW, lowLimit));

        double mediumLimit = ruleParameterRepository.findById("BUDGET_LIMIT_MEDIUM").get().getParamValue();
        templateData.add(new BudgetRuleTemplateModel(Budget.MEDIUM, mediumLimit));

        // ObjectDataCompiler spaja listu objekata i templejt [cite: 598, 609]
        ObjectDataCompiler compiler = new ObjectDataCompiler();
        String generatedDrl = compiler.compile(templateData, templateStream);

        // --- Kreiranje KieBase sa SVIM pravilima (statičkim + dinamičkim) ---
        KieServices ks = KieServices.Factory.get();
        KieHelper kieHelper = new KieHelper();

        // Dodajemo dinamički generisan DRL
        kieHelper.addContent(generatedDrl, ResourceType.DRL);

        // Dodajemo i sva ostala, statička pravila iz KJAR-a
        kieHelper.addResource(ks.getResources().newClassPathResource("rules/filtering.drl"), ResourceType.DRL);
        kieHelper.addResource(ks.getResources().newClassPathResource("rules/scoring.drl"), ResourceType.DRL);
        kieHelper.addResource(ks.getResources().newClassPathResource("rules/itinerary.drl"), ResourceType.DRL);
        kieHelper.addResource(ks.getResources().newClassPathResource("rules/alerts.drl"), ResourceType.DRL);

        // Verifikacija i build
        Results results = kieHelper.verify();
        if (results.hasMessages(Message.Level.ERROR)) {
            throw new IllegalStateException("Greške prilikom kompajliranja Drools pravila: " + results.getMessages());
        }
        KieBase kieBase = kieHelper.build();

        // --- Izvršavanje faza sa novom KieBase ---
        // Kreiramo sesiju iz nove, kompletne baze znanja
        KieSession session = kieBase.newKieSession();
        try {
            // FAZA 1: Filtriranje i Bodovanje
            session.getAgenda().getAgendaGroup("scoring").setFocus();
            session.insert(preferences);
            locationRepository.findAll().forEach(session::insert);
            ruleParameterRepository.findAll().forEach(session::insert);
            session.fireAllRules();

            List<Recommendation> recommendations = new ArrayList<>();
            QueryResults scoringResults = session.getQueryResults("getRecommendations");
            for (QueryResultsRow row : scoringResults) {
                recommendations.add((Recommendation) row.get("$rec"));
            }

            List<Location> topLocations = recommendations.stream()
                    .sorted(Comparator.comparing(Recommendation::getScore).reversed())
                    .limit(preferences.getNumberOfDays() * 2)
                    .map(Recommendation::getLocation)
                    .collect(Collectors.toList());

            // FAZA 2: Sklapanje Plana i Upozorenja
            session.getAgenda().getAgendaGroup("itinerary").setFocus();
            topLocations.forEach(session::insert);
            routeRepository.findAll().forEach(session::insert);
            List<ItineraryItem> itinerary = new ArrayList<>();
            session.setGlobal("itinerary", itinerary);
            session.fireAllRules();

            session.getAgenda().getAgendaGroup("alerts").setFocus();
            itinerary.forEach(session::insert);
            List<Alert> alerts = new ArrayList<>();
            session.setGlobal("alerts", alerts);
            Set<String> uniqueAlerts = new HashSet<>();
            session.setGlobal("uniqueAlerts", uniqueAlerts);
            session.fireAllRules();

            itinerary.sort(Comparator.comparing(ItineraryItem::getDay));
            return new TravelPlanResponse(itinerary, alerts);
        } finally {
            session.dispose();
        }
    }
}