package com.inicu.models;

import java.util.HashMap;
import java.util.List;

import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.BabyfeedDetail;
import com.inicu.postgres.entities.NursingIntakeOutput;
import com.inicu.postgres.entities.RefMedfrequency;
import com.inicu.postgres.entities.SaFeedIntolerance;


public class FeedIntorelanceJSON {

	SaFeedIntolerance feedIntolerance;
	
	List<SaFeedIntolerance> listfeedIntolerance;
	private List<Object[]> associatedEvents;
	
	ResponseMessageObject response;
	
	String userId="";
	
	List<BabyPrescription> prescriptionList;
	
	List<NursingIntakeOutput> nutritionList;
	
	private String orderSelectedText;

	HashMap<Object, List<RefTestslist>> testsList;
	
	List<KeyValueObj> treatmentAction;

	private BabyfeedDetail lastBabyFeedObject;
	
	List<RefMedfrequency> medicineFrequency;

	private String inactiveProgressNote;

	public BabyfeedDetail getLastBabyFeedObject() {
		return lastBabyFeedObject;
	}

	public void setLastBabyFeedObject(BabyfeedDetail lastBabyFeedObject) {
		this.lastBabyFeedObject = lastBabyFeedObject;
	}

	//	public FeedIntorelanceJSON(SaFeedIntolerance feedIntolerance, List<SaFeedIntolerance> listfeedIntolerance,
//			ResponseMessageObject response, String userId) {
//		super();
//		this.feedIntolerance = feedIntolerance;
//		this.listfeedIntolerance = listfeedIntolerance;
//		this.response = response;
//		this.userId = userId;
//	}

	public List<Object[]> getAssociatedEvents() {
		return associatedEvents;
	}
	
	public String getOrderSelectedText() {
		return orderSelectedText;
	}

	public void setOrderSelectedText(String orderSelectedText) {
		this.orderSelectedText = orderSelectedText;
	}

	public List<BabyPrescription> getPrescriptionList() {
		return prescriptionList;
	}

	public void setPrescriptionList(List<BabyPrescription> prescriptionList) {
		this.prescriptionList = prescriptionList;
	}

	public void setAssociatedEvents(List<Object[]> associatedEvents) {
		this.associatedEvents = associatedEvents;
	}

	public ResponseMessageObject getResponse() {
		return response;
	}

	public void setResponse(ResponseMessageObject response) {
		this.response = response;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public SaFeedIntolerance getFeedIntolerance() {
		return feedIntolerance;
	}

	public void setFeedIntolerance(SaFeedIntolerance feedIntolerance) {
		this.feedIntolerance = feedIntolerance;
	}

	public List<SaFeedIntolerance> getListfeedIntolerance() {
		return listfeedIntolerance;
	}

	public void setListfeedIntolerance(List<SaFeedIntolerance> listfeedIntolerance) {
		this.listfeedIntolerance = listfeedIntolerance;
	}

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

	public List<NursingIntakeOutput> getNutritionList() {
		return nutritionList;
	}

	public void setNutritionList(List<NursingIntakeOutput> nutritionList) {
		this.nutritionList = nutritionList;
	}

	public List<RefMedfrequency> getMedicineFrequency() {
		return medicineFrequency;
	}

	public void setMedicineFrequency(List<RefMedfrequency> medicineFrequency) {
		this.medicineFrequency = medicineFrequency;
	}

	public String getInactiveProgressNote() {
		return inactiveProgressNote;
	}

	public void setInactiveProgressNote(String inactiveProgressNote) {
		this.inactiveProgressNote = inactiveProgressNote;
	}

	@Override
	public String toString() {
		return "FeedIntorelanceJSON [feedIntolerance=" + feedIntolerance + ", listfeedIntolerance="
				+ listfeedIntolerance + ", associatedEvents=" + associatedEvents + ", response=" + response
				+ ", userId=" + userId + ", prescriptionList=" + prescriptionList + ", orderSelectedText="
				+ orderSelectedText + ", testsList=" + testsList + ", treatmentAction=" + treatmentAction + "]";
	}	
		
}
