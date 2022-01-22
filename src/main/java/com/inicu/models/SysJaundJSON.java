package com.inicu.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.SaJaundice;

public class SysJaundJSON {
	ResponseMessageObject response;
	JaundDropDownsJSON dropDowns;
	SaJaundice jaundice;
	List<SaJaundice> listJaundice;
	String userId = "";
	String motherBloodGroup = "";
	String babyBloodGroup = "";
	Float maxTcB = Float.valueOf("0");
	Object phototherapyDuration ;
	JaundiceInNeoNatesGraphObj jaundicePhototherapyGraph;
	Integer bindscore;
	HashMap<Object, List<Object[]>> jaundiceGraphData;
	
	//not in use anymore
	//private List<Object[]> associatedEvents;

	List<BabyPrescription> prescriptionList;
	
	private String orderSelectedText;

	private String pastIcdCodeList;
	
	String pastBindScoredate;
	
	private String inactiveProgressNote;
	
	String prevPhotoType;
	
	public SysJaundJSON() {
		super();
		this.jaundice = new SaJaundice();
		this.response = new ResponseMessageObject();
		this.listJaundice = new ArrayList<SaJaundice>();
		this.prescriptionList = new ArrayList<BabyPrescription>();

	}

	public ResponseMessageObject getResponse() {
		return response;
	}

	public void setResponse(ResponseMessageObject response) {
		this.response = response;
	}

	public JaundDropDownsJSON getDropDowns() {
		return dropDowns;
	}

	public void setDropDowns(JaundDropDownsJSON dropDowns) {
		this.dropDowns = dropDowns;
	}

	public SaJaundice getJaundice() {
		return jaundice;
	}

	public void setJaundice(SaJaundice jaundice) {
		this.jaundice = jaundice;
	}

	public List<SaJaundice> getListJaundice() {
		return listJaundice;
	}

	public void setListJaundice(List<SaJaundice> listJaundice) {
		this.listJaundice = listJaundice;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public HashMap<Object, List<Object[]>> getJaundiceGraphData() {
		return jaundiceGraphData;
	}

	public void setJaundiceGraphData(HashMap<Object, List<Object[]>> jaundiceGraphData) {
		this.jaundiceGraphData = jaundiceGraphData;
	}

	public Float getMaxTcB() {
		return maxTcB;
	}

	public void setMaxTcB(Float maxTcB) {
		this.maxTcB = maxTcB;
	}

	public JaundiceInNeoNatesGraphObj getJaundicePhototherapyGraph() {
		return jaundicePhototherapyGraph;
	}

	public void setJaundicePhototherapyGraph(JaundiceInNeoNatesGraphObj jaundicePhototherapyGraph) {
		this.jaundicePhototherapyGraph = jaundicePhototherapyGraph;
	}

//	public List<Object[]> getAssociatedEvents() {
//		return associatedEvents;
//	}
//
//	public void setAssociatedEvents(List<Object[]> associatedEvents) {
//		this.associatedEvents = associatedEvents;
//	}

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

	public String getMotherBloodGroup() {
		return motherBloodGroup;
	}

	public void setMotherBloodGroup(String motherBloodGroup) {
		this.motherBloodGroup = motherBloodGroup;
	}

	public String getPastIcdCodeList() {
		return pastIcdCodeList;
	}

	public void setPastIcdCodeList(String pastIcdCodeList) {
		this.pastIcdCodeList = pastIcdCodeList;
	}

	public Object getPhototherapyDuration() {
		return phototherapyDuration;
	}

	public void setPhototherapyDuration(Object phototherapyDuration) {
		this.phototherapyDuration = phototherapyDuration;
	}

	public String getBabyBloodGroup() {
		return babyBloodGroup;
	}

	public void setBabyBloodGroup(String babyBloodGroup) {
		this.babyBloodGroup = babyBloodGroup;
	}

	public String getPastBindScoredate() {
		return pastBindScoredate;
	}

	public void setPastBindScoredate(String pastBindScoredate) {
		this.pastBindScoredate = pastBindScoredate;
	}

	public Integer getBindscore() {
		return bindscore;
	}

	public void setBindscore(Integer bindscore) {
		this.bindscore = bindscore;
	}

	public String getInactiveProgressNote() {
		return inactiveProgressNote;
	}

	public void setInactiveProgressNote(String inactiveProgressNote) {
		this.inactiveProgressNote = inactiveProgressNote;
	}

	public String getPrevPhotoType() {
		return prevPhotoType;
	}

	public void setPrevPhotoType(String prevPhotoType) {
		this.prevPhotoType = prevPhotoType;
	}

}
