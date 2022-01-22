package com.inicu.models;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.classmate.util.ResolvedTypeCache.Key;
import com.inicu.postgres.entities.RefMedfrequency;


public class ShockDropDownsJSON {

	
	List<KeyValueObj> causeOfShock;
	
	List<KeyValueObj> orderInvestigation;
	
	List<KeyValueObj> treatmentAction;
	
	List<String> hours;
	
	List<String> minutes;
	
	HashMap<Object, List<RefTestslist>> testsList;
	
	List<RefMedfrequency> medicineFrequency;

	public List<String> getHours() {
		return hours;
	}

	public void setHours(List<String> hours) {
		this.hours = hours;
	}

	public List<KeyValueObj> getOrderInvestigation() {
		return orderInvestigation;
	}

	public void setOrderInvestigation(List<KeyValueObj> orderInvestigation) {
		this.orderInvestigation = orderInvestigation;
	}

	public List<KeyValueObj> getTreatmentAction() {
		return treatmentAction;
	}

	public void setTreatmentAction(List<KeyValueObj> treatmentAction) {
		this.treatmentAction = treatmentAction;
	}

	public HashMap<Object, List<RefTestslist>> getTestsList() {
		return testsList;
	}

	public void setTestsList(HashMap<Object, List<RefTestslist>> testsList) {
		this.testsList = testsList;
	}

	public List<String> getMinutes() {
		return minutes;
	}

	public void setMinutes(List<String> minutes) {
		this.minutes = minutes;
	}

	public List<KeyValueObj> getCauseOfShock() {
		return causeOfShock;
	}

	public void setCauseOfShock(List<KeyValueObj> causeOfShock) {
		this.causeOfShock = causeOfShock;
	}

	public List<RefMedfrequency> getMedicineFrequency() {
		return medicineFrequency;
	}

	public void setMedicineFrequency(List<RefMedfrequency> medicineFrequency) {
		this.medicineFrequency = medicineFrequency;
	}
	
}
