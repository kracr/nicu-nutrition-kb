package com.inicu.models;

import java.util.ArrayList;
import java.util.List;

import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.SaInfectClabsi;

public class ClabsiEventPOJO {

	SaInfectClabsi currentEvent;
	
	ClabsiPastEventPOJO pastEvents;
	
	List<SaInfectClabsi> pastClabsiList;
	
	List<BabyPrescription> prescriptionList;
	

	public ClabsiEventPOJO() {
		super();
		// TODO Auto-generated constructor stub
		this.currentEvent = new SaInfectClabsi();
		
		this.pastEvents = new ClabsiPastEventPOJO();	
		
		this.pastClabsiList = new ArrayList<SaInfectClabsi>();
	}

	public SaInfectClabsi getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(SaInfectClabsi currentEvent) {
		this.currentEvent = currentEvent;
	}

	public ClabsiPastEventPOJO getPastEvents() {
		return pastEvents;
	}

	public void setPastEvents(ClabsiPastEventPOJO pastEvents) {
		this.pastEvents = pastEvents;
	}

	public List<SaInfectClabsi> getPastClabsiList() {
		return pastClabsiList;
	}

	public void setPastClabsiList(List<SaInfectClabsi> pastClabsiList) {
		this.pastClabsiList = pastClabsiList;
	}

	public List<BabyPrescription> getPrescriptionList() {
		return prescriptionList;
	}

	public void setPrescriptionList(List<BabyPrescription> prescriptionList) {
		this.prescriptionList = prescriptionList;
	}
	
	
	
}
