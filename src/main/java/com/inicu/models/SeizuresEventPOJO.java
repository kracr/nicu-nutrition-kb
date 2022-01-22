package com.inicu.models;

import java.util.ArrayList;
import java.util.List;

import com.inicu.postgres.entities.BabyPrescription;

public class SeizuresEventPOJO {

	SeizuresCurrentEventPOJO currentEvent;

	SeizuresPastEventPOJO pastEvents;

	List<BabyPrescription> prescriptionList;
	
	String inactiveProgressNote;

	public SeizuresEventPOJO() {
		super();
		this.currentEvent = new SeizuresCurrentEventPOJO();
		this.pastEvents = new SeizuresPastEventPOJO();
		this.prescriptionList = new ArrayList<BabyPrescription>();
	}

	public SeizuresCurrentEventPOJO getCurrentEvent() {
		return currentEvent;
	}

	public SeizuresPastEventPOJO getPastEvents() {
		return pastEvents;
	}

	public void setCurrentEvent(SeizuresCurrentEventPOJO currentEvent) {
		this.currentEvent = currentEvent;
	}

	public void setPastEvents(SeizuresPastEventPOJO pastEvents) {
		this.pastEvents = pastEvents;
	}

	public List<BabyPrescription> getPrescriptionList() {
		return prescriptionList;
	}

	public void setPrescriptionList(List<BabyPrescription> prescriptionList) {
		this.prescriptionList = prescriptionList;
	}

	public String getInactiveProgressNote() {
		return inactiveProgressNote;
	}

	public void setInactiveProgressNote(String inactiveProgressNote) {
		this.inactiveProgressNote = inactiveProgressNote;
	}

}
