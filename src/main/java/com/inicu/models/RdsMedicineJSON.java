package com.inicu.models;

import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.NursingorderRdsMedicine;

public class RdsMedicineJSON {
	NursingorderRdsMedicine nursingRdsMedicine;
	String medicineName;
	Float dose;
	String route;
	String frequency;
	String medicationType;
	String calculateDose;
	
	public NursingorderRdsMedicine getNursingRdsMedicine() {
		return nursingRdsMedicine;
	}
	public void setNursingRdsMedicine(NursingorderRdsMedicine nursingRdsMedicine) {
		this.nursingRdsMedicine = nursingRdsMedicine;
	}
	public String getMedicineName() {
		return medicineName;
	}
	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}
	public Float getDose() {
		return dose;
	}
	public void setDose(Float dose) {
		this.dose = dose;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getMedicationType() {
		return medicationType;
	}
	public void setMedicationType(String medicationType) {
		this.medicationType = medicationType;
	}
	public String getCalculateDose() {
		return calculateDose;
	}
	public void setCalculateDose(String calculateDose) {
		this.calculateDose = calculateDose;
	}
	
}
