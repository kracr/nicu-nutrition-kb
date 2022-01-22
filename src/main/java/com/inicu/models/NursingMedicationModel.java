package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.AssessmentMedication;
import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.CentralLine;
import com.inicu.postgres.entities.DoctorBloodProducts;
import com.inicu.postgres.entities.MedicationPreparation;
import com.inicu.postgres.entities.NursingBloodproduct;
import com.inicu.postgres.entities.NursingHeplock;
import com.inicu.postgres.entities.NursingMedication;
import com.inicu.postgres.entities.RefMedBrand;
import com.inicu.postgres.entities.RefMedType;
import com.inicu.postgres.entities.RefMedfrequency;

public class NursingMedicationModel {

	private NursingMedication currentEmptyObj;

	private BabyPrescription currentMedicationObj;

	private List<NursingMedication> pastNursingList;

	private List<BabyPrescription> allMedicationList;

	private MedicationPreparation medicationPreparationObj;

	private List<MedicationPreparation> medicationPreparationList;

	private List<RefMedfrequency> freqList;

	private List<RefMedType> typeList;

	private AssessmentMedication currentAssessmentMedicationObj;

	private List<AssessmentMedication> assessmentMedicineList;

	private List<AssessmentMedication> pastAssessmentMedicineList;

	private NursingBloodproduct emptyBloodProductObj;

	private NursingBloodproduct currentBloodProductObj;

	private List<NursingBloodproduct> pastNursingBloodProductList;

	private List<DoctorBloodProducts> bloodProductList;
	
	private List<CentralLine> centralLineList;
	
	private List<NursingHeplock> nursingHeplockList;
	
	private NursingHeplock currentHeplockObj;

	private List<RefMedBrand> brandList;

	public NursingMedicationModel() {
		super();
		this.currentEmptyObj = new NursingMedication();
		this.currentEmptyObj.setStop_flag(false);
		this.emptyBloodProductObj = new NursingBloodproduct();
		this.medicationPreparationObj = new MedicationPreparation();
		this.currentBloodProductObj = new NursingBloodproduct();
		this.currentHeplockObj = new NursingHeplock();
	}

	public NursingMedication getCurrentEmptyObj() {
		return currentEmptyObj;
	}

	public void setCurrentEmptyObj(NursingMedication currentEmptyObj) {
		this.currentEmptyObj = currentEmptyObj;
	}

	public BabyPrescription getCurrentMedicationObj() {
		return currentMedicationObj;
	}

	public void setCurrentMedicationObj(BabyPrescription currentMedicationObj) {
		this.currentMedicationObj = currentMedicationObj;
	}

	public List<NursingMedication> getPastNursingList() {
		return pastNursingList;
	}

	public void setPastNursingList(List<NursingMedication> pastNursingList) {
		this.pastNursingList = pastNursingList;
	}

	public List<BabyPrescription> getAllMedicationList() {
		return allMedicationList;
	}

	public void setAllMedicationList(List<BabyPrescription> allMedicationList) {
		this.allMedicationList = allMedicationList;
	}

	public MedicationPreparation getMedicationPreparationObj() {
		return medicationPreparationObj;
	}

	public void setMedicationPreparationObj(MedicationPreparation medicationPreparationObj) {
		this.medicationPreparationObj = medicationPreparationObj;
	}

	public List<MedicationPreparation> getMedicationPreparationList() {
		return medicationPreparationList;
	}

	public void setMedicationPreparationList(List<MedicationPreparation> medicationPreparationList) {
		this.medicationPreparationList = medicationPreparationList;
	}

	public List<RefMedfrequency> getFreqList() {
		return freqList;
	}

	public void setFreqList(List<RefMedfrequency> freqList) {
		this.freqList = freqList;
	}

	public List<RefMedType> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<RefMedType> typeList) {
		this.typeList = typeList;
	}

	public AssessmentMedication getCurrentAssessmentMedicationObj() {
		return currentAssessmentMedicationObj;
	}

	public void setCurrentAssessmentMedicationObj(AssessmentMedication currentAssessmentMedicationObj) {
		this.currentAssessmentMedicationObj = currentAssessmentMedicationObj;
	}

	public List<AssessmentMedication> getAssessmentMedicineList() {
		return assessmentMedicineList;
	}

	public void setAssessmentMedicineList(List<AssessmentMedication> assessmentMedicineList) {
		this.assessmentMedicineList = assessmentMedicineList;
	}

	public List<AssessmentMedication> getPastAssessmentMedicineList() {
		return pastAssessmentMedicineList;
	}

	public void setPastAssessmentMedicineList(List<AssessmentMedication> pastAssessmentMedicineList) {
		this.pastAssessmentMedicineList = pastAssessmentMedicineList;
	}

	public NursingBloodproduct getEmptyBloodProductObj() {
		return emptyBloodProductObj;
	}

	public void setEmptyBloodProductObj(NursingBloodproduct emptyBloodProductObj) {
		this.emptyBloodProductObj = emptyBloodProductObj;
	}

	public NursingBloodproduct getCurrentBloodProductObj() {
		return currentBloodProductObj;
	}

	public void setCurrentBloodProductObj(NursingBloodproduct currentBloodProductObj) {
		this.currentBloodProductObj = currentBloodProductObj;
	}

	public List<NursingBloodproduct> getPastNursingBloodProductList() {
		return pastNursingBloodProductList;
	}

	public void setPastNursingBloodProductList(List<NursingBloodproduct> pastNursingBloodProductList) {
		this.pastNursingBloodProductList = pastNursingBloodProductList;
	}

	public List<DoctorBloodProducts> getBloodProductList() {
		return bloodProductList;
	}

	public void setBloodProductList(List<DoctorBloodProducts> bloodProductList) {
		this.bloodProductList = bloodProductList;
	}

	public List<CentralLine> getCentralLineList() {
		return centralLineList;
	}

	public void setCentralLineList(List<CentralLine> centralLineList) {
		this.centralLineList = centralLineList;
	}

	public List<NursingHeplock> getNursingHeplockList() {
		return nursingHeplockList;
	}

	public void setNursingHeplockList(List<NursingHeplock> nursingHeplockList) {
		this.nursingHeplockList = nursingHeplockList;
	}

	public NursingHeplock getCurrentHeplockObj() {
		return currentHeplockObj;
	}

	public void setCurrentHeplockObj(NursingHeplock currentHeplockObj) {
		this.currentHeplockObj = currentHeplockObj;
	}

	public List<RefMedBrand> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<RefMedBrand> brandList) {
		this.brandList = brandList;
	}

}
