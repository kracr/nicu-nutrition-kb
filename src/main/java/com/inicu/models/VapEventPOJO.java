package com.inicu.models;

import java.util.ArrayList;
import java.util.List;

import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.SaInfectVap;

public class VapEventPOJO {
	SaInfectVap currentEvent;
	
	VapPastEventPOJO pastEvents;
	
	List<SaInfectVap> pastVapList;
	
	List<BabyPrescription> prescriptionList;
	
	String inactiveProgressNote;

	public VapEventPOJO() {
		super();
		// TODO Auto-generated constructor stub
		this.currentEvent = new SaInfectVap();
		
		this.pastEvents = new VapPastEventPOJO();
		
		this.pastVapList = new ArrayList<SaInfectVap>();
	}

	public SaInfectVap getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(SaInfectVap currentEvent) {
		this.currentEvent = currentEvent;
	}

	public VapPastEventPOJO getPastEvents() {
		return pastEvents;
	}

	public void setPastEvents(VapPastEventPOJO pastEvents) {
		this.pastEvents = pastEvents;
	}

	public List<SaInfectVap> getPastVapList() {
		return pastVapList;
	}

	public void setPastVapList(List<SaInfectVap> pastVapList) {
		this.pastVapList = pastVapList;
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
