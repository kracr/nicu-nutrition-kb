package com.inicu.models;

import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.SaInfectIntrauterine;

import java.util.ArrayList;
import java.util.List;

public class IntrauterineEventPOJO {

	SaInfectIntrauterine currentEvent;
	
	IntrauterinePastEventPOJO pastEvents;

	List<BabyPrescription> prescriptionList;

	public IntrauterineEventPOJO() {
		super();
		// TODO Auto-generated constructor stub
		this.currentEvent = new SaInfectIntrauterine();
		this.pastEvents = new IntrauterinePastEventPOJO();
		this.prescriptionList=new ArrayList<BabyPrescription>();
	}

	public SaInfectIntrauterine getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(SaInfectIntrauterine currentEvent) {
		this.currentEvent = currentEvent;
	}

	public IntrauterinePastEventPOJO getPastEvents() {
		return pastEvents;
	}

	public void setPastEvents(IntrauterinePastEventPOJO pastEvents) {
		this.pastEvents = pastEvents;
	}

	public List<BabyPrescription> getPrescriptionList() {
		return prescriptionList;
	}

	public void setPrescriptionList(List<BabyPrescription> prescriptionList) {
		this.prescriptionList = prescriptionList;
	}

}
