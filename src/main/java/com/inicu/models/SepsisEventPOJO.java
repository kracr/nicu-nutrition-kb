package com.inicu.models;

import java.util.ArrayList;
import java.util.List;

import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.SaSepsis;

public class SepsisEventPOJO {

	SaSepsis currentEvent;

	SepsisPastEventPOJO pastEvents;

	List<BabyPrescription> prescriptionList;
	
	List<SaSepsis> pastSepsisList;
	
	String inactiveProgressNote;

	public SepsisEventPOJO() {
		super();
		// TODO Auto-generated constructor stub
		this.currentEvent = new SaSepsis();
		this.pastEvents = new SepsisPastEventPOJO();
		this.prescriptionList = new ArrayList<BabyPrescription>();
		this.pastSepsisList = new ArrayList<SaSepsis>();
	}

	public SaSepsis getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(SaSepsis currentEvent) {
		this.currentEvent = currentEvent;
	}

	public SepsisPastEventPOJO getPastEvents() {
		return pastEvents;
	}

	public void setPastEvents(SepsisPastEventPOJO pastEvents) {
		this.pastEvents = pastEvents;
	}

	public List<BabyPrescription> getPrescriptionList() {
		return prescriptionList;
	}

	public void setPrescriptionList(List<BabyPrescription> prescriptionList) {
		this.prescriptionList = prescriptionList;
	}

	public List<SaSepsis> getPastSepsisList() {
		return pastSepsisList;
	}

	public void setPastSepsisList(List<SaSepsis> pastSepsisList) {
		this.pastSepsisList = pastSepsisList;
	}

	public String getInactiveProgressNote() {
		return inactiveProgressNote;
	}

	public void setInactiveProgressNote(String inactiveProgressNote) {
		this.inactiveProgressNote = inactiveProgressNote;
	}
	
	
	

}
