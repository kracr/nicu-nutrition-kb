package com.inicu.models;

import java.util.List;

public class CnsDropDowns {
	
	List<KeyValueObj> typeOfSeizure;
	List<KeyValueObj> causeofSeizure;
	List<KeyValueObj> medicationForSeizures;
	
	public List<KeyValueObj> getMedicationForSeizures() {
		return medicationForSeizures;
	}
	public void setMedicationForSeizures(List<KeyValueObj> medicationForSeizures) {
		this.medicationForSeizures = medicationForSeizures;
	}
	public List<KeyValueObj> getTypeOfSeizure() {
		return typeOfSeizure;
	}
	public void setTypeOfSeizure(List<KeyValueObj> typeOfSeizure) {
		this.typeOfSeizure = typeOfSeizure;
	}
	public List<KeyValueObj> getCauseofSeizure() {
		return causeofSeizure;
	}
	public void setCauseofSeizure(List<KeyValueObj> causeofSeizure) {
		this.causeofSeizure = causeofSeizure;
	}
	
	
}
