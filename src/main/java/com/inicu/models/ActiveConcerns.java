package com.inicu.models;

public class ActiveConcerns {

	private String eventName;
	
	private String medication;
	
	private String treatment;
	
	

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getMedication() {
		return medication;
	}

	public void setMedication(String medication) {
		this.medication = medication;
	}

	@Override
	public String toString() {
		return "ActiveConcerns [eventName=" + eventName + ", medication=" + medication + "]";
	}
	
	
	
}
