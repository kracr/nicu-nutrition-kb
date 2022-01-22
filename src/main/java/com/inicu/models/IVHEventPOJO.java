package com.inicu.models;

import com.inicu.postgres.entities.BabyPrescription;

import java.util.ArrayList;
import java.util.List;

public class IVHEventPOJO {
	
	IVHCurrentEventPOJO currentEvent;
	
	IVHPastEventPOJO pastEvents;

	List<BabyPrescription> prescriptionList;

	public IVHEventPOJO() {
		super();
		this.currentEvent = new IVHCurrentEventPOJO();
		this.pastEvents = new IVHPastEventPOJO();
		this.prescriptionList = new ArrayList<BabyPrescription>();
	}

	public IVHCurrentEventPOJO getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(IVHCurrentEventPOJO currentEvent) {
		this.currentEvent = currentEvent;
	}

	public IVHPastEventPOJO getPastEvents() {
		return pastEvents;
	}

	public void setPastEvents(IVHPastEventPOJO pastEvents) {
		this.pastEvents = pastEvents;
	}

	public List<BabyPrescription> getPrescriptionList() {
		return prescriptionList;
	}

	public void setPrescriptionList(List<BabyPrescription> prescriptionList) {
		this.prescriptionList = prescriptionList;
	}
}
