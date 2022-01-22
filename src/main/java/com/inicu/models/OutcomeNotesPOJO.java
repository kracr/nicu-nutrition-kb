package com.inicu.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Transient;

import com.inicu.postgres.entities.OutcomeNotes;

public class OutcomeNotesPOJO {
	
	
	ResponseMessageObject response;
	
	OutcomeNotes notes;
	
	List<ActiveConcerns> activeConcernsList ;

	public ResponseMessageObject getResponse() {
		return response;
	}

	public void setResponse(ResponseMessageObject response) {
		this.response = response;
	}

	public OutcomeNotes getNotes() {
		return notes;
	}

	public void setNotes(OutcomeNotes notes) {
		this.notes = notes;
	}

	public List<ActiveConcerns> getActiveConcernsList() {
		return activeConcernsList;
	}

	public void setActiveConcernsList(List<ActiveConcerns> activeConcernsList) {
		this.activeConcernsList = activeConcernsList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	private String uhid;
//	
//	private String condition;
//	
//	private String spo2;
//
//	private String heartrate;
//	
//	private String meanBp;
//	
//	private String rr;
//	
//	private String temp;
//	
//	private String respiratoryStatus;
//	
//	private Float growthVelocity;
//	
//	private String reason;
//	
//	private String modeOfTransportation;
//	
//	private String progressNotes;
//	
//	private String outcome;
//	
//	private String mode;
//	
//	private String type;
//	
//	private String frequency;
//	
//	private Float volumePerFeed;
//	
//	private String activeAssessment;
//	
//	private String fluidType;
//	
//	private String actualDextroseConc;
//	
//	private Boolean isEnteralGiven;
//	
//	private Boolean isparenteralGiven;
//	
//	
//	
//	
//	
//
//	public String getUhid() {
//		return uhid;
//	}
//
//	public void setUhid(String uhid) {
//		this.uhid = uhid;
//	}
//
//	public String getCondition() {
//		return condition;
//	}
//
//	public void setCondition(String condition) {
//		this.condition = condition;
//	}
//
//	public String getSpo2() {
//		return spo2;
//	}
//
//	public void setSpo2(String spo2) {
//		this.spo2 = spo2;
//	}
//
//	public String getHeartrate() {
//		return heartrate;
//	}
//
//	public void setHeartrate(String heartrate) {
//		this.heartrate = heartrate;
//	}
//
//	public String getMeanBp() {
//		return meanBp;
//	}
//
//	public void setMeanBp(String meanBp) {
//		this.meanBp = meanBp;
//	}
//
//	public String getRr() {
//		return rr;
//	}
//
//	public void setRr(String rr) {
//		this.rr = rr;
//	}
//
//	public String getTemp() {
//		return temp;
//	}
//
//	public void setTemp(String temp) {
//		this.temp = temp;
//	}
//
//	public String getRespiratoryStatus() {
//		return respiratoryStatus;
//	}
//
//	public void setRespiratoryStatus(String respiratoryStatus) {
//		this.respiratoryStatus = respiratoryStatus;
//	}
//
//	public Float getGrowthVelocity() {
//		return growthVelocity;
//	}
//
//	public void setGrowthVelocity(Float growthVelocity) {
//		this.growthVelocity = growthVelocity;
//	}
//
//	public String getReason() {
//		return reason;
//	}
//
//	public void setReason(String reason) {
//		this.reason = reason;
//	}
//
//	public String getModeOfTransportation() {
//		return modeOfTransportation;
//	}
//
//	public void setModeOfTransportation(String modeOfTransportation) {
//		this.modeOfTransportation = modeOfTransportation;
//	}
//
//	public String getProgressNotes() {
//		return progressNotes;
//	}
//
//	public void setProgressNotes(String progressNotes) {
//		this.progressNotes = progressNotes;
//	}
//
//	public String getOutcome() {
//		return outcome;
//	}
//
//	public void setOutcome(String outcome) {
//		this.outcome = outcome;
//	}
//
//	public String getMode() {
//		return mode;
//	}
//
//	public void setMode(String mode) {
//		this.mode = mode;
//	}
//
//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}
//
//	
//	public String getFrequency() {
//		return frequency;
//	}
//
//	public void setFrequency(String frequency) {
//		this.frequency = frequency;
//	}
//
//	public Float getVolumePerFeed() {
//		return volumePerFeed;
//	}
//
//	public void setVolumePerFeed(Float volumePerFeed) {
//		this.volumePerFeed = volumePerFeed;
//	}
//
//	public String getActiveAssessment() {
//		return activeAssessment;
//	}
//
//	public void setActiveAssessment(String activeAssessment) {
//		this.activeAssessment = activeAssessment;
//	}
//
//	public String getFluidType() {
//		return fluidType;
//	}
//
//	public void setFluidType(String fluidType) {
//		this.fluidType = fluidType;
//	}
//
//	public String getActualDextroseConc() {
//		return actualDextroseConc;
//	}
//
//	public void setActualDextroseConc(String actualDextroseConc) {
//		this.actualDextroseConc = actualDextroseConc;
//	}
//
//	public Boolean getIsEnteralGiven() {
//		return isEnteralGiven;
//	}
//
//	public void setIsEnteralGiven(Boolean isEnteralGiven) {
//		this.isEnteralGiven = isEnteralGiven;
//	}
//
//	public Boolean getIsparenteralGiven() {
//		return isparenteralGiven;
//	}
//
//	public void setIsparenteralGiven(Boolean isparenteralGiven) {
//		this.isparenteralGiven = isparenteralGiven;
//	}
//	
	
	
	
	
	
	
	
	
	
	



}
