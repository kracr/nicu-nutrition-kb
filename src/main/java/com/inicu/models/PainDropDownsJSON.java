package com.inicu.models;

import java.util.HashMap;
import java.util.List;

import com.inicu.postgres.entities.RefPainNonPharmacolgicalList;
import com.inicu.postgres.entities.RefPainProceduresList;

public class PainDropDownsJSON {
	
	List<KeyValueObj> nonPharmacologicalMeasures;
	
	List<KeyValueObj> orderInvestigation;
	
	List<KeyValueObj> treatmentAction;
	
	List<String> hours;
	
	List<String> minutes;
		
	HashMap<Object, List<RefTestslist>> testsList; 
	
	List<KeyValueObj> procedureList;

	public List<String> getHours() {
		return hours;
	}

	public void setHours(List<String> hours) {
		this.hours = hours;
	}

	public List<KeyValueObj> getNonPharmacologicalMeasures() {
		return nonPharmacologicalMeasures;
	}

	public void setNonPharmacologicalMeasures(List<KeyValueObj> nonPharmacologicalMeasures) {
		this.nonPharmacologicalMeasures = nonPharmacologicalMeasures;
	}

	public List<KeyValueObj> getProcedureList() {
		return procedureList;
	}

	public void setProcedureList(List<KeyValueObj> procedureList) {
		this.procedureList = procedureList;
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
}
