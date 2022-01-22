package com.inicu.models;

public class TherapeuticHypothermiaPOJO {
    TherapeuticHypothermiaCurrentEventPOJO currentEvent;

    TherapeuticHypothermiaPastEventPOJO pastEvents;


    public TherapeuticHypothermiaPOJO() {
        this.currentEvent = new TherapeuticHypothermiaCurrentEventPOJO();
        this.pastEvents = new TherapeuticHypothermiaPastEventPOJO();
    }

    public TherapeuticHypothermiaCurrentEventPOJO getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(TherapeuticHypothermiaCurrentEventPOJO currentEvent) {
        this.currentEvent = currentEvent;
    }

    public TherapeuticHypothermiaPastEventPOJO getPastEvents() {
        return pastEvents;
    }

    public void setPastEvents(TherapeuticHypothermiaPastEventPOJO pastEvents) {
        this.pastEvents = pastEvents;
    }
}
