package com.inicu.models;

import java.util.HashMap;
import java.util.List;

import com.inicu.postgres.entities.RefMedfrequency;

public class RenalDropDownsJSON {
	
	List<KeyValueItem> preRenalCausesList;
	List<KeyValueItem> intrinsicCausesList;
	List<KeyValueItem> postRenalCausesList;
	
	List<String> hours;
	
	List<String> minutes;

	HashMap<Object, List<RefTestslist>> testsList;
	
	List<KeyValueObj> treatmentAction;
	
	List<RefMedfrequency> medicineFrequency;
	
	
	
	

	public List<KeyValueItem> getPreRenalCausesList() {
		return preRenalCausesList;
	}

	public void setPreRenalCausesList(List<KeyValueItem> preRenalCausesList) {
		this.preRenalCausesList = preRenalCausesList;
	}

	public List<KeyValueItem> getIntrinsicCausesList() {
		return intrinsicCausesList;
	}

	public void setIntrinsicCausesList(List<KeyValueItem> intrinsicCausesList) {
		this.intrinsicCausesList = intrinsicCausesList;
	}

	public List<KeyValueItem> getPostRenalCausesList() {
		return postRenalCausesList;
	}

	public void setPostRenalCausesList(List<KeyValueItem> postRenalCausesList) {
		this.postRenalCausesList = postRenalCausesList;
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

	public List<String> getHours() {
		return hours;
	}

	public void setHours(List<String> hours) {
		this.hours = hours;
	}

	public List<String> getMinutes() {
		return minutes;
	}

	public void setMinutes(List<String> minutes) {
		this.minutes = minutes;
	}

	public List<RefMedfrequency> getMedicineFrequency() {
		return medicineFrequency;
	}

	public void setMedicineFrequency(List<RefMedfrequency> medicineFrequency) {
		this.medicineFrequency = medicineFrequency;
	}
	
	
	

}
