package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.Medicines;
import com.inicu.postgres.entities.RefMedSolutions;
import com.inicu.postgres.entities.RefMedicine;

public class MedicineDropDownsPOJO {
	
	List<Medicines> medicines;
	
	List<Medicines> frequency;
	
	List<Medicines> medtype;
	
	List<Medicines> masterMedicineList;
	
	List<RefMedicine> recommendedNeofax;
	
	List<RefMedSolutions> solutionsList;

	public List<Medicines> getMedicines() {
		return medicines;
	}

	public void setMedicines(List<Medicines> medicines) {
		this.medicines = medicines;
	}

	public List<Medicines> getFrequency() {
		return frequency;
	}

	public void setFrequency(List<Medicines> frequency) {
		this.frequency = frequency;
	}

	public List<Medicines> getMedtype() {
		return medtype;
	}

	public void setMedtype(List<Medicines> medtype) {
		this.medtype = medtype;
	}

	public List<RefMedicine> getRecommendedNeofax() {
		return recommendedNeofax;
	}

	public void setRecommendedNeofax(List<RefMedicine> recommendedNeofax) {
		this.recommendedNeofax = recommendedNeofax;
	}

	public List<RefMedSolutions> getSolutionsList() {
		return solutionsList;
	}

	public void setSolutionsList(List<RefMedSolutions> solutionsList) {
		this.solutionsList = solutionsList;
	}

	public List<Medicines> getMasterMedicineList() {
		return masterMedicineList;
	}

	public void setMasterMedicineList(List<Medicines> masterMedicineList) {
		this.masterMedicineList = masterMedicineList;
	}

}
