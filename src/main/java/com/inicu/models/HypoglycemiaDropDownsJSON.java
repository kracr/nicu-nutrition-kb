package com.inicu.models;

import java.util.HashMap;
import java.util.List;

import com.inicu.postgres.entities.RefMedfrequency;

public class HypoglycemiaDropDownsJSON {

	HashMap<Object, List<RefTestslist>> testsList;

	List<KeyValueObj> treatmentAction;
	
	List<KeyValueObj> causeOfHypoglycemia;
	
	List<KeyValueObj> riskFactorsList;
	
	List<RefMedfrequency> medicineFrequency;
	
	public HashMap<Object, List<RefTestslist>> getTestsList() {
		return testsList;
	}

	public void setTestsList(HashMap<Object, List<RefTestslist>> testsList) {
		this.testsList = testsList;
	}

	public List<KeyValueObj> getTreatmentAction() {
		return treatmentAction;
	}

	public void setTreatmentAction(List<KeyValueObj> treatmentAction) {
		this.treatmentAction = treatmentAction;
	}

	public List<KeyValueObj> getCauseOfHypoglycemia() {
		return causeOfHypoglycemia;
	}

	public void setCauseOfHypoglycemia(List<KeyValueObj> causeOfHypoglycemia) {
		this.causeOfHypoglycemia = causeOfHypoglycemia;
	}

	public List<KeyValueObj> getRiskFactorsList() {
		return riskFactorsList;
	}

	public void setRiskFactorsList(List<KeyValueObj> riskFactorsList) {
		this.riskFactorsList = riskFactorsList;
	}

	public List<RefMedfrequency> getMedicineFrequency() {
		return medicineFrequency;
	}

	public void setMedicineFrequency(List<RefMedfrequency> medicineFrequency) {
		this.medicineFrequency = medicineFrequency;
	}
	
	
	
	

}
