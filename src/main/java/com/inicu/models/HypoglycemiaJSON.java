package com.inicu.models;

import java.util.ArrayList;
import java.util.List;

import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.SaHypoglycemia;

public class HypoglycemiaJSON {

	SaHypoglycemia currentHypoglycemia;

	List<SaHypoglycemia> listHypoglycemia;
	
	private String orderSelectedText;

	ResponseMessageObject response;

	String userId = "";

	List<BabyPrescription> prescriptionList;
	
	HypoglycemiaDropDownsJSON dropDowns;

	String inactiveProgressNote;

	public HypoglycemiaJSON(){
		this.currentHypoglycemia=new SaHypoglycemia();
		this.listHypoglycemia=new ArrayList<>();
		this.prescriptionList=new ArrayList<>();
		this.dropDowns=new HypoglycemiaDropDownsJSON();
	}

	public String getOrderSelectedText() {
		return orderSelectedText;
	}

	public void setOrderSelectedText(String orderSelectedText) {
		this.orderSelectedText = orderSelectedText;
	}

	public HypoglycemiaDropDownsJSON getDropDowns() {
		return dropDowns;
	}

	public void setDropDowns(HypoglycemiaDropDownsJSON dropDowns) {
		this.dropDowns = dropDowns;
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

	public List<BabyPrescription> getPrescriptionList() {
		return prescriptionList;
	}

	public void setPrescriptionList(List<BabyPrescription> prescriptionList) {
		this.prescriptionList = prescriptionList;
	}

	public SaHypoglycemia getCurrentHypoglycemia() {
		return currentHypoglycemia;
	}

	public void setCurrentHypoglycemia(SaHypoglycemia currentHypoglycemia) {
		this.currentHypoglycemia = currentHypoglycemia;
	}

	public List<SaHypoglycemia> getListHypoglycemia() {
		return listHypoglycemia;
	}

	public void setListHypoglycemia(List<SaHypoglycemia> listHypoglycemia) {
		this.listHypoglycemia = listHypoglycemia;
	}

	public String getInactiveProgressNote() {
		return inactiveProgressNote;
	}

	public void setInactiveProgressNote(String inactiveProgressNote) {
		this.inactiveProgressNote = inactiveProgressNote;
	}
}
