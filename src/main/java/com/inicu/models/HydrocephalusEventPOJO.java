package com.inicu.models;

import com.inicu.postgres.entities.BabyPrescription;

import java.util.ArrayList;
import java.util.List;

public class HydrocephalusEventPOJO {
    HydrocephalusCurrentEventPOJO currentEvent;

    HydrocephalusPastEventPOJO pastEvents;
    List<BabyPrescription> prescriptionList;

    public HydrocephalusEventPOJO() {
        super();
        this.currentEvent = new HydrocephalusCurrentEventPOJO();
        this.pastEvents = new HydrocephalusPastEventPOJO();
        this.prescriptionList = new ArrayList<BabyPrescription>();
    }

    public List<BabyPrescription> getPrescriptionList() {
        return prescriptionList;
    }

    public void setPrescriptionList(List<BabyPrescription> prescriptionList) {
        this.prescriptionList = prescriptionList;
    }

    public HydrocephalusCurrentEventPOJO getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(HydrocephalusCurrentEventPOJO currentEvent) {
        this.currentEvent = currentEvent;
    }

    public HydrocephalusPastEventPOJO getPastEvents() {
        return pastEvents;
    }

    public void setPastEvents(HydrocephalusPastEventPOJO pastEvents) {
        this.pastEvents = pastEvents;
    }
}
