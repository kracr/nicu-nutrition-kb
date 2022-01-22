package com.inicu.models;

import java.util.ArrayList;
import java.util.List;

import com.inicu.postgres.entities.BabyPrescription;

import javax.persistence.Transient;

public class AsphyxiaEventPOJO {

	AshpyxiaCurrentEventPOJO currentEvent;

	AshpyxiaPastEventPOJO pastEvents;
	
	String inactiveProgressNote;

	@Transient
	private Boolean isTherapeuticHypothermiaSelected;

	TherapeuticHypothermiaPOJO therapeuticHypothermiaEvent;

	List<BabyPrescription> prescriptionList;

	public AsphyxiaEventPOJO() {
		super();
		this.currentEvent = new AshpyxiaCurrentEventPOJO();
		this.pastEvents = new AshpyxiaPastEventPOJO();
		this.therapeuticHypothermiaEvent=new TherapeuticHypothermiaPOJO();
		this.prescriptionList = new ArrayList<BabyPrescription>();
		this.isTherapeuticHypothermiaSelected=false;
	}

	public Boolean getTherapeuticHypothermiaSelected() {
		return isTherapeuticHypothermiaSelected;
	}

	public void setTherapeuticHypothermiaSelected(Boolean therapeuticHypothermiaSelected) {
		isTherapeuticHypothermiaSelected = therapeuticHypothermiaSelected;
	}

	public AshpyxiaCurrentEventPOJO getCurrentEvent() {
		return currentEvent;
	}

	public AshpyxiaPastEventPOJO getPastEvents() {
		return pastEvents;
	}

	public void setCurrentEvent(AshpyxiaCurrentEventPOJO currentEvent) {
		this.currentEvent = currentEvent;
	}

	public void setPastEvents(AshpyxiaPastEventPOJO pastEvents) {
		this.pastEvents = pastEvents;
	}

	public List<BabyPrescription> getPrescriptionList() {
		return prescriptionList;
	}

	public void setPrescriptionList(List<BabyPrescription> prescriptionList) {
		this.prescriptionList = prescriptionList;
	}

	public TherapeuticHypothermiaPOJO getTherapeuticHypothermiaEvent() {
		return therapeuticHypothermiaEvent;
	}

	public void setTherapeuticHypothermiaEvent(TherapeuticHypothermiaPOJO therapeuticHypothermiaEvent) {
		this.therapeuticHypothermiaEvent = therapeuticHypothermiaEvent;
	}

	public String getInactiveProgressNote() {
		return inactiveProgressNote;
	}

	public void setInactiveProgressNote(String inactiveProgressNote) {
		this.inactiveProgressNote = inactiveProgressNote;
	}
}
