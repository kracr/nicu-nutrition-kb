package com.inicu.models;

import com.inicu.postgres.entities.BabyPrescription;
import java.util.ArrayList;
import java.util.List;

public class EncephalopathyEventPOJO {
    EncephalopathyCurrentEventPOJO currentEvent;
    EncephalopathyPastEventPOJO pastEvents;
    List<BabyPrescription> prescriptionList;

    public EncephalopathyEventPOJO(){
        super();
        this.currentEvent = new EncephalopathyCurrentEventPOJO();
        this.pastEvents = new EncephalopathyPastEventPOJO();
        this.prescriptionList = new ArrayList<BabyPrescription>();
    }

    public EncephalopathyCurrentEventPOJO getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(EncephalopathyCurrentEventPOJO currentEvent) {
        this.currentEvent = currentEvent;
    }

    public EncephalopathyPastEventPOJO getPastEvents() {
        return pastEvents;
    }

    public void setPastEvents(EncephalopathyPastEventPOJO pastEvents) {
        this.pastEvents = pastEvents;
    }

    public List<BabyPrescription> getPrescriptionList() {
        return prescriptionList;
    }

    public void setPrescriptionList(List<BabyPrescription> prescriptionList) {
        this.prescriptionList = prescriptionList;
    }

}
