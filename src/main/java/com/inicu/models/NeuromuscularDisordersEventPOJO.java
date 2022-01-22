package com.inicu.models;

import com.inicu.postgres.entities.BabyPrescription;

import java.util.ArrayList;
import java.util.List;

public class NeuromuscularDisordersEventPOJO {

    NeuromuscularDisordersCurrentEventPOJO currentEvent;

    NeuromuscularDisordersPastEventPOJO pastEvents;

    List<BabyPrescription> prescriptionList;

    public NeuromuscularDisordersEventPOJO() {
        super();
        this.currentEvent = new NeuromuscularDisordersCurrentEventPOJO();
        this.pastEvents = new NeuromuscularDisordersPastEventPOJO();
        this.prescriptionList = new ArrayList<BabyPrescription>();
    }

    public NeuromuscularDisordersCurrentEventPOJO getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(NeuromuscularDisordersCurrentEventPOJO currentEvent) {
        this.currentEvent = currentEvent;
    }

    public NeuromuscularDisordersPastEventPOJO getPastEvents() {
        return pastEvents;
    }

    public void setPastEvents(NeuromuscularDisordersPastEventPOJO pastEvents) {
        this.pastEvents = pastEvents;
    }

    public List<BabyPrescription> getPrescriptionList() {
        return prescriptionList;
    }

    public void setPrescriptionList(List<BabyPrescription> prescriptionList) {
        this.prescriptionList = prescriptionList;
    }
}
