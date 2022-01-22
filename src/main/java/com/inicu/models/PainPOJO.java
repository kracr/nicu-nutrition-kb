package com.inicu.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.SaJaundice;
import com.inicu.postgres.entities.SaPain;

public class PainPOJO {

    private SaPain pain;
    
	ResponseMessageObject response;
	PainDropDownsJSON dropDowns;
	List<SaPain> listPain;
	String userId = "";

	List<BabyPrescription> prescriptionList;
	
	private String orderSelectedText;
	

	public PainPOJO() {
		super();
		this.pain = new SaPain();
		this.response = new ResponseMessageObject();
		this.listPain = new ArrayList<SaPain>();
		this.prescriptionList = new ArrayList<BabyPrescription>();

	}


	public SaPain getPain() {
		return pain;
	}


	public void setPain(SaPain pain) {
		this.pain = pain;
	}


	public ResponseMessageObject getResponse() {
		return response;
	}


	public void setResponse(ResponseMessageObject response) {
		this.response = response;
	}


	public PainDropDownsJSON getDropDowns() {
		return dropDowns;
	}


	public void setDropDowns(PainDropDownsJSON dropDowns) {
		this.dropDowns = dropDowns;
	}

	public List<SaPain> getListPain() {
		return listPain;
	}


	public void setListPain(List<SaPain> listPain) {
		this.listPain = listPain;
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


	public String getOrderSelectedText() {
		return orderSelectedText;
	}


	public void setOrderSelectedText(String orderSelectedText) {
		this.orderSelectedText = orderSelectedText;
	}
	
}