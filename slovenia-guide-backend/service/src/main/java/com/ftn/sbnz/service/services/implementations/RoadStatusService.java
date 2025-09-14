package com.ftn.sbnz.service.services.implementations;

import com.ftn.sbnz.model.events.RoadStatusEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RoadStatusService {
    private final List<RoadStatusEvent> activeEvents = new ArrayList<>();

    public void addEvent(RoadStatusEvent event) {
        event.setTimestamp(new Date());
        // U realnoj aplikaciji, ovde biste imali logiku da uklonite starije događaje
        this.activeEvents.add(event);
    }

    public List<RoadStatusEvent> getActiveEvents() {
        return new ArrayList<>(this.activeEvents);
    }
}