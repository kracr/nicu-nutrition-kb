package com.inicu.models;

import com.inicu.postgres.entities.*;

import java.util.HashMap;
import java.util.List;

public class NursingAllDataPOJO {

	List<BabyVisit> anthropometryList;

	List<NursingVitalparameter> vitalList;

	List<NursingEpisode> episodeList;

	List<KeyValueObj> ventilatorModeDropDown;

	List<RefMasterfeedtype> refFeedTypeList;

	List<BabyfeedDetail> doctorNutritionOrder;

	List<NursingIntakeOutput> intakeOutputList;
	
	List<NursingIntakeOutput> pastIntakeOutputList;

	List<NursingMedication> medicationList;
	
	List<NursingMedication> pastMedicationList;

	List<BabyPrescription> allMedicationList;
	
	List<BabyPrescription> currentMedicationList;

	List<RefMedfrequency> freqList;

	List<AssessmentMedication> pastAssessmentMedicineList;

	List<NursingNote> notesList;

	List<NursingBloodGasVentilator> bgVentilatorList;
	
	List<NursingBloodproduct> allPastNursingBloodProductList;
	
	StableNote stableNoteObj;
	
	List<NursingBloodproduct> pastNursingBloodProductList;
	
	List<NursingHeplock> pastHeplockList;

	List<NursingHeplock> heplockList;
	
	List<DoctorBloodProducts> bloodProductList;
	
	FeedCalculatorPOJO feedCalulator;
	
	List<EtSuction> etSuctionList;
	
	List<Object> procedureList;
	
	List<InvestigationOrdered> invOrderedList;
	
	String printFormatType;
	
	RefNursingOutputParameters nursingOutputParameter;

	String feedMethodStr = "";

	List<EnFeedDetail> enFeedList;

	List<KeyValueObj> logoImage;

	List<NursingEpisode> nursingEpisodeList;
	
	String today_weight;
	
	List<NurseExecutionOrders> nursingExecutionList;

	List<NurseTasks> nurseTasksCommentsList;

	public List<NurseTasks> getNurseTasksCommentsList() {
		return nurseTasksCommentsList;
	}

	public void setNurseTasksCommentsList(List<NurseTasks> nurseTasksCommentsList) {
		this.nurseTasksCommentsList = nurseTasksCommentsList;
	}

	HashMap<String,AlertMinMax> clinicalAlertSettingsList;

	public HashMap<String, AlertMinMax> getClinicalAlertSettingsList() {
		return clinicalAlertSettingsList;
	}

	public void setClinicalAlertSettingsList(HashMap<String, AlertMinMax> clinicalAlertSettingsList) {
		this.clinicalAlertSettingsList = clinicalAlertSettingsList;
	}

	public List<NursingEpisode> getNursingEpisodeList() {
		return nursingEpisodeList;
	}

	public void setNursingEpisodeList(List<NursingEpisode> nursingEpisodeList) {
		this.nursingEpisodeList = nursingEpisodeList;
	}


	public Float getNursingWeight() {
		return nursingWeight;
	}

	public void setNursingWeight(Float nursingWeight) {
		this.nursingWeight = nursingWeight;
	}

	Float nursingWeight;

	public List<KeyValueObj> getLogoImage() {
		return logoImage;
	}

	public void setLogoImage(List<KeyValueObj> logoImage) {
		this.logoImage = logoImage;
	}

	public NursingAllDataPOJO() {
		super();
		this.clinicalAlertSettingsList=new HashMap<>();
	}

	public List<BabyVisit> getAnthropometryList() {
		return anthropometryList;
	}

	public void setAnthropometryList(List<BabyVisit> anthropometryList) {
		this.anthropometryList = anthropometryList;
	}

	public List<NursingVitalparameter> getVitalList() {
		return vitalList;
	}

	public void setVitalList(List<NursingVitalparameter> vitalList) {
		this.vitalList = vitalList;
	}

	public List<NursingEpisode> getEpisodeList() {
		return episodeList;
	}

	public void setEpisodeList(List<NursingEpisode> episodeList) {
		this.episodeList = episodeList;
	}

	public List<KeyValueObj> getVentilatorModeDropDown() {
		return ventilatorModeDropDown;
	}

	public void setVentilatorModeDropDown(List<KeyValueObj> ventilatorModeDropDown) {
		this.ventilatorModeDropDown = ventilatorModeDropDown;
	}

	public List<RefMasterfeedtype> getRefFeedTypeList() {
		return refFeedTypeList;
	}

	public void setRefFeedTypeList(List<RefMasterfeedtype> refFeedTypeList) {
		this.refFeedTypeList = refFeedTypeList;
	}

	public List<BabyfeedDetail> getDoctorNutritionOrder() {
		return doctorNutritionOrder;
	}

	public void setDoctorNutritionOrder(List<BabyfeedDetail> doctorNutritionOrder) {
		this.doctorNutritionOrder = doctorNutritionOrder;
	}

	public List<NursingIntakeOutput> getIntakeOutputList() {
		return intakeOutputList;
	}

	public void setIntakeOutputList(List<NursingIntakeOutput> intakeOutputList) {
		this.intakeOutputList = intakeOutputList;
	}

	public List<NursingMedication> getMedicationList() {
		return medicationList;
	}

	public void setMedicationList(List<NursingMedication> medicationList) {
		this.medicationList = medicationList;
	}

	public List<BabyPrescription> getAllMedicationList() {
		return allMedicationList;
	}

	public void setAllMedicationList(List<BabyPrescription> allMedicationList) {
		this.allMedicationList = allMedicationList;
	}

	public List<RefMedfrequency> getFreqList() {
		return freqList;
	}

	public void setFreqList(List<RefMedfrequency> freqList) {
		this.freqList = freqList;
	}

	public List<AssessmentMedication> getPastAssessmentMedicineList() {
		return pastAssessmentMedicineList;
	}

	public void setPastAssessmentMedicineList(List<AssessmentMedication> pastAssessmentMedicineList) {
		this.pastAssessmentMedicineList = pastAssessmentMedicineList;
	}

	public List<NursingNote> getNotesList() {
		return notesList;
	}

	public void setNotesList(List<NursingNote> notesList) {
		this.notesList = notesList;
	}

	public List<NursingBloodGasVentilator> getBgVentilatorList() {
		return bgVentilatorList;
	}

	public void setBgVentilatorList(List<NursingBloodGasVentilator> bgVentilatorList) {
		this.bgVentilatorList = bgVentilatorList;
	}

	public StableNote getStableNoteObj() {
		return stableNoteObj;
	}

	public void setStableNoteObj(StableNote stableNoteObj) {
		this.stableNoteObj = stableNoteObj;
	}

	public List<NursingHeplock> getHeplockList() {
		return heplockList;
	}

	public void setHeplockList(List<NursingHeplock> heplockList) {
		this.heplockList = heplockList;
	}

	public List<NursingBloodproduct> getPastNursingBloodProductList() {
		return pastNursingBloodProductList;
	}

	public void setPastNursingBloodProductList(
			List<NursingBloodproduct> pastNursingBloodProductList) {
		this.pastNursingBloodProductList = pastNursingBloodProductList;
	}

	public List<DoctorBloodProducts> getBloodProductList() {
		return bloodProductList;
	}

	public void setBloodProductList(List<DoctorBloodProducts> bloodProductList) {
		this.bloodProductList = bloodProductList;
	}

	public FeedCalculatorPOJO getFeedCalulator() {
		return feedCalulator;
	}

	public void setFeedCalulator(FeedCalculatorPOJO feedCalulator) {
		this.feedCalulator = feedCalulator;
	}

	public List<NursingMedication> getPastMedicationList() {
		return pastMedicationList;
	}

	public void setPastMedicationList(List<NursingMedication> pastMedicationList) {
		this.pastMedicationList = pastMedicationList;
	}

	public List<NursingBloodproduct> getAllPastNursingBloodProductList() {
		return allPastNursingBloodProductList;
	}

	public void setAllPastNursingBloodProductList(List<NursingBloodproduct> allPastNursingBloodProductList) {
		this.allPastNursingBloodProductList = allPastNursingBloodProductList;
	}

	public List<NursingHeplock> getPastHeplockList() {
		return pastHeplockList;
	}

	public void setPastHeplockList(List<NursingHeplock> pastHeplockList) {
		this.pastHeplockList = pastHeplockList;
	}

	public List<NursingIntakeOutput> getPastIntakeOutputList() {
		return pastIntakeOutputList;
	}

	public void setPastIntakeOutputList(List<NursingIntakeOutput> pastIntakeOutputList) {
		this.pastIntakeOutputList = pastIntakeOutputList;
	}

	public List<EtSuction> getEtSuctionList() {
		return etSuctionList;
	}

	public void setEtSuctionList(List<EtSuction> etSuctionList) {
		this.etSuctionList = etSuctionList;
	}

	public List<BabyPrescription> getCurrentMedicationList() {
		return currentMedicationList;
	}

	public void setCurrentMedicationList(List<BabyPrescription> currentMedicationList) {
		this.currentMedicationList = currentMedicationList;
	}

	public List<Object> getProcedureList() {
		return procedureList;
	}

	public void setProcedureList(List<Object> procedureList) {
		this.procedureList = procedureList;
	}

	public List<InvestigationOrdered> getInvOrderedList() {
		return invOrderedList;
	}

	public void setInvOrderedList(List<InvestigationOrdered> invOrderedList) {
		this.invOrderedList = invOrderedList;
	}

	public String getPrintFormatType() {
		return printFormatType;
	}

	public void setPrintFormatType(String printFormatType) {
		this.printFormatType = printFormatType;
	}

	public RefNursingOutputParameters getNursingOutputParameter() {
		return nursingOutputParameter;
	}

	public void setNursingOutputParameter(RefNursingOutputParameters nursingOutputParameter) {
		this.nursingOutputParameter = nursingOutputParameter;
	}

	public String getFeedMethodStr() {
		return feedMethodStr;
	}

	public void setFeedMethodStr(String feedMethodStr) {
		this.feedMethodStr = feedMethodStr;
	}

	public List<EnFeedDetail> getEnFeedList() {
		return enFeedList;
	}

	public void setEnFeedList(List<EnFeedDetail> enFeedList) {
		this.enFeedList = enFeedList;
	}

	public String getToday_weight() {
		return today_weight;
	}

	public void setToday_weight(String today_weight) {
		this.today_weight = today_weight;
	}

	public List<NurseExecutionOrders> getNursingExecutionList() {
		return nursingExecutionList;
	}

	public void setNursingExecutionList(List<NurseExecutionOrders> nursingExecutionList) {
		this.nursingExecutionList = nursingExecutionList;
	}

	@Override
	public String toString() {
		return "NursingAllDataPOJO [anthropometryList=" + anthropometryList + ", vitalList=" + vitalList
				+ ", episodeList=" + episodeList + ", ventilatorModeDropDown=" + ventilatorModeDropDown
				+ ", refFeedTypeList=" + refFeedTypeList + ", doctorNutritionOrder=" + doctorNutritionOrder
				+ ", intakeOutputList=" + intakeOutputList + ", pastIntakeOutputList=" + pastIntakeOutputList
				+ ", medicationList=" + medicationList + ", pastMedicationList=" + pastMedicationList
				+ ", allMedicationList=" + allMedicationList + ", currentMedicationList=" + currentMedicationList
				+ ", freqList=" + freqList + ", pastAssessmentMedicineList=" + pastAssessmentMedicineList
				+ ", notesList=" + notesList + ", bgVentilatorList=" + bgVentilatorList
				+ ", allPastNursingBloodProductList=" + allPastNursingBloodProductList + ", stableNoteObj="
				+ stableNoteObj + ", pastNursingBloodProductList=" + pastNursingBloodProductList + ", pastHeplockList="
				+ pastHeplockList + ", heplockList=" + heplockList + ", bloodProductList=" + bloodProductList
				+ ", feedCalulator=" + feedCalulator + ", etSuctionList=" + etSuctionList + ", procedureList="
				+ procedureList + ", invOrderedList=" + invOrderedList + ", printFormatType=" + printFormatType
				+ ", nursingOutputParameter=" + nursingOutputParameter + ", feedMethodStr=" + feedMethodStr
				+ ", enFeedList=" + enFeedList + ", logoImage=" + logoImage + ", nursingEpisodeList="
				+ nursingEpisodeList + ", today_weight=" + today_weight + ", nursingExecutionList="
				+ nursingExecutionList + "]";
	}

}