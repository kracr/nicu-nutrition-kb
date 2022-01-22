package com.inicu.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.inicu.postgres.entities.BabyPrescription;

public class MedicalAdministrationObj {

	BabyPrescription babyPrescription;
	List<MedicineDateTimeObj> givenMedincineDateTime;
	private Integer frequency;
	private Boolean isMedicineSelected;
	private Date smallestStartDate ;
	
	
	
	public Integer getFrequency() {
		return frequency;
	}
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	public MedicalAdministrationObj() {
		super();
		this.isMedicineSelected = false;
		this.frequency = 3;
		this.givenMedincineDateTime = new ArrayList<MedicineDateTimeObj>();
	}
	public BabyPrescription getBabyPrescription() {
		return babyPrescription;
	}
	public void setBabyPrescription(BabyPrescription babyPrescription) {
		this.babyPrescription = babyPrescription;
	}
	public List<MedicineDateTimeObj> getGivenMedincineDateTime() {
		return givenMedincineDateTime;
	}
	public void setGivenMedincineDateTime(
			List<MedicineDateTimeObj> givenMedincineDateTime) {
		this.givenMedincineDateTime = givenMedincineDateTime;
	}
	public Boolean getIsMedicineSelected() {
		return isMedicineSelected;
	}
	public void setIsMedicineSelected(Boolean isMedicineSelected) {
		this.isMedicineSelected = isMedicineSelected;
	}
	public Date getSmallestStartDate() {
		return smallestStartDate;
	}
	public void setSmallestStartDate(Date smallestStartDate) {
		this.smallestStartDate = smallestStartDate;
	}
	

	
	
	
	
	
	
}
