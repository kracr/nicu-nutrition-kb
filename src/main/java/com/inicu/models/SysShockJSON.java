package com.inicu.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.inicu.postgres.entities.*;

public class SysShockJSON {
	ResponseMessageObject response;
	ShockDropDownsJSON dropDowns;
	SaShock shock;
	List<SaShock> listShock;
	String userId = "";
	//not in use anymore
	//private List<Object[]> associatedEvents;

	List<BabyPrescription> prescriptionList;
	
	private String orderSelectedText;
	
	private RespSupport respSupport;
	
	private BabyfeedDetail lastBabyFeedObject;

	private List<RespSupport> respUsage;

    private String inactiveProgressNote;

    private List<BabyPrescription> inotropesList;

	public SysShockJSON() {
		super();
		this.shock = new SaShock();
		this.response = new ResponseMessageObject();
		this.listShock = new ArrayList<SaShock>();
		this.prescriptionList = new ArrayList<BabyPrescription>();
		this.inotropesList = new ArrayList<>();
	}

	public ResponseMessageObject getResponse() {
		return response;
	}

	public void setResponse(ResponseMessageObject response) {
		this.response = response;
	}

	public ShockDropDownsJSON getDropDowns() {
		return dropDowns;
	}

	public void setDropDowns(ShockDropDownsJSON dropDowns) {
		this.dropDowns = dropDowns;
	}

	public SaShock getShock() {
		return shock;
	}

	public void setShock(SaShock shock) {
		this.shock = shock;
	}

	public List<SaShock> getListShock() {
		return listShock;
	}

	public void setListShock(List<SaShock> listShock) {
		this.listShock = listShock;
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

	public RespSupport getRespSupport() {
		return respSupport;
	}

	public void setRespSupport(RespSupport respSupport) {
		this.respSupport = respSupport;
	}
	

	public BabyfeedDetail getLastBabyFeedObject() {
		return lastBabyFeedObject;
	}

	public void setLastBabyFeedObject(BabyfeedDetail lastBabyFeedObject) {
		this.lastBabyFeedObject = lastBabyFeedObject;
	}

	public List<RespSupport> getRespUsage() {
		return respUsage;
	}

	public void setRespUsage(List<RespSupport> respUsage) {
		this.respUsage = respUsage;
	}

    public String getInactiveProgressNote() {
        return inactiveProgressNote;
    }

    public void setInactiveProgressNote(String inactiveProgressNote) {
        this.inactiveProgressNote = inactiveProgressNote;
    }

	public List<BabyPrescription> getInotropesList() {
		return inotropesList;
	}

	public void setInotropesList(List<BabyPrescription> inotropesList) {
		this.inotropesList = inotropesList;
	}
}
