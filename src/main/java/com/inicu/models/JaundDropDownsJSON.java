package com.inicu.models;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.classmate.util.ResolvedTypeCache.Key;
import com.inicu.postgres.entities.RefMedfrequency;


public class JaundDropDownsJSON {

	
	List<KeyValueObj> causeOfJaundice;
	
	List<KeyValueObj> orderInvestigation;
	
	List<KeyValueObj> treatmentAction;
	
	List<String> hours;
	
	List<String> minutes;
	
	List<KeyValueObj> riskFactor;
	
	HashMap<Object, List<RefTestslist>> testsList; 
	
	List<KeyValueObj> icdCauseOfJaundice;
	
	List<RefMedfrequency> medicineFrequency;
	
	public List<KeyValueObj> getRiskFactor() {
		return riskFactor;
	}

	public void setRiskFactor(List<KeyValueObj> riskFactor) {
		this.riskFactor = riskFactor;
	}

	public List<String> getHours() {
		return hours;
	}

	public void setHours(List<String> hours) {
		this.hours = hours;
	}

	public List<KeyValueObj> getCauseOfJaundice() {
		return causeOfJaundice;
	}

	public void setCauseOfJaundice(List<KeyValueObj> causeOfJaundice) {
		this.causeOfJaundice = causeOfJaundice;
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

	public List<KeyValueObj> getIcdCauseOfJaundice() {
		return icdCauseOfJaundice;
	}

	public void setIcdCauseOfJaundice(List<KeyValueObj> icdCauseOfJaundice) {
		this.icdCauseOfJaundice = icdCauseOfJaundice;
	}

	public List<RefMedfrequency> getMedicineFrequency() {
		return medicineFrequency;
	}

	public void setMedicineFrequency(List<RefMedfrequency> medicineFrequency) {
		this.medicineFrequency = medicineFrequency;
	}
	
}
