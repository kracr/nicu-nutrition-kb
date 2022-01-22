package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.BabyfeedDetail;
import com.inicu.postgres.entities.EnFeedDetail;
import com.inicu.postgres.entities.PeritonealDialysis;
import com.inicu.postgres.entities.SaRenalfailure;

public class RenalFailureJSON {

	SaRenalfailure renalFailure;
	
	List<SaRenalfailure> listRenalFailure;
	
	//not in use anymore
	//private List<Object[]> associatedEvents;
	
	ResponseMessageObject response;
	
	String userId="";
	
	List<BabyPrescription> prescriptionList;
	
	List<PeritonealDialysis> peritonealDialysisObjectList;
	
	private String orderSelectedText;
	
	RenalDropDownsJSON dropDowns;
	
	BabyfeedDetail babyFeedObj;
	
	List<EnFeedDetail> enFeedList;
	
	String feedMethodStr = "";

	String inactiveProgressNote;

//	public RenalFailureJSON(SaRenalfailure renalFailure, List<SaRenalfailure> listRenalFailure,
//			ResponseMessageObject response, String userId) {
//		super();
//		this.renalFailure = renalFailure;
//		this.listRenalFailure = listRenalFailure;
//		this.response = response;
//		this.userId = userId;
//	}
	
	
	
	
//
//	public List<Object[]> getAssociatedEvents() {
//		return associatedEvents;
//	}

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

	public BabyfeedDetail getBabyFeedObj() {
		return babyFeedObj;
	}

	public void setBabyFeedObj(BabyfeedDetail babyFeedObj) {
		this.babyFeedObj = babyFeedObj;
	}

	public RenalDropDownsJSON getDropDowns() {
		return dropDowns;
	}

	public void setDropDowns(RenalDropDownsJSON dropDowns) {
		this.dropDowns = dropDowns;
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

//	public void setAssociatedEvents(List<Object[]> associatedEvents) {
//		this.associatedEvents = associatedEvents;
//	}

	public SaRenalfailure getRenalFailure() {
		return renalFailure;
	}

	public void setRenalFailure(SaRenalfailure renalFailure) {
		this.renalFailure = renalFailure;
	}

	public List<SaRenalfailure> getListRenalFailure() {
		return listRenalFailure;
	}

	public void setListRenalFailure(List<SaRenalfailure> listRenalFailure) {
		this.listRenalFailure = listRenalFailure;
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

	public List<PeritonealDialysis> getPeritonealDialysisObjectList() {
		return peritonealDialysisObjectList;
	}

	public void setPeritonealDialysisObjectList(List<PeritonealDialysis> peritonealDialysisObjectList) {
		this.peritonealDialysisObjectList = peritonealDialysisObjectList;
	}

	public String getInactiveProgressNote() {
		return inactiveProgressNote;
	}

	public void setInactiveProgressNote(String inactiveProgressNote) {
		this.inactiveProgressNote = inactiveProgressNote;
	}

	//	RenalDropDownJSON dropdowns;
	
	
	
		
}
