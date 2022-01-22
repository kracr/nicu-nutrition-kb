package com.inicu.models;

public class AssessmentRespSystemPOJO {

	private String uhid;

	private String loggedUser;

	private Object ageAtOnset;

	private Object gestation;

	private Object workingWeight;

	private String respSupportDuration = "";

	private String lfDuration = "";

	private String hfDuration = "";

	private String cpapDuration = "";

	private String mvDuration = "";

	private String hfoDuration = "";

	private RespSystemDropDowns dropDowns;

	private RespiratorySystemEventPOJO respSystemObject;
	
	//not in use anymore 
	private String systemStatus;
	
	private Object pma;
	
	private String bpdRespTemplate;
	
	private Object currentGestationWeek;
	
	private Object currentGestationDay;
	
	public AssessmentRespSystemPOJO() {
		super();
		this.respSystemObject = new RespiratorySystemEventPOJO();
	}

	public String getRespSupportDuration() {
		return respSupportDuration;
	}

	public void setRespSupportDuration(String respSupportDuration) {
		this.respSupportDuration = respSupportDuration;
	}

	public String getLfDuration() {
		return lfDuration;
	}

	public void setLfDuration(String lfDuration) {
		this.lfDuration = lfDuration;
	}
	
	public String getSystemStatus() {
		return systemStatus;
	}

	public void setSystemStatus(String systemStatus) {
		this.systemStatus = systemStatus;
	}
	
	
	public String getHfDuration() {
		return hfDuration;
	}

	public void setHfDuration(String hfDuration) {
		this.hfDuration = hfDuration;
	}

	public String getCpapDuration() {
		return cpapDuration;
	}

	public void setCpapDuration(String cpapDuration) {
		this.cpapDuration = cpapDuration;
	}

	public String getMvDuration() {
		return mvDuration;
	}

	public void setMvDuration(String mvDuration) {
		this.mvDuration = mvDuration;
	}

	public String getHfoDuration() {
		return hfoDuration;
	}

	public void setHfoDuration(String hfoDuration) {
		this.hfoDuration = hfoDuration;
	}

	public RespSystemDropDowns getDropDowns() {
		return dropDowns;
	}

	public RespiratorySystemEventPOJO getRespSystemObject() {
		return respSystemObject;
	}

	public void setDropDowns(RespSystemDropDowns dropDowns) {
		this.dropDowns = dropDowns;
	}

	public void setRespSystemObject(RespiratorySystemEventPOJO respSystemObject) {
		this.respSystemObject = respSystemObject;
	}

	public Object getAgeAtOnset() {
		return ageAtOnset;
	}

	public void setAgeAtOnset(Object ageAtOnset) {
		this.ageAtOnset = ageAtOnset;
	}

	public Object getGestation() {
		return gestation;
	}

	public void setGestation(Object gestation) {
		this.gestation = gestation;
	}

	public String getUhid() {
		return uhid;
	}

	public String getLoggedUser() {
		return loggedUser;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}

	public Object getWorkingWeight() {
		return workingWeight;
	}

	public void setWorkingWeight(Object workingWeight) {
		this.workingWeight = workingWeight;
	}

	public Object getPma() {
		return pma;
	}

	public void setPma(Object pma) {
		this.pma = pma;
	}

	public String getBpdRespTemplate() {
		return bpdRespTemplate;
	}

	public void setBpdRespTemplate(String bpdRespTemplate) {
		this.bpdRespTemplate = bpdRespTemplate;
	}

	public Object getCurrentGestationWeek() {
		return currentGestationWeek;
	}

	public void setCurrentGestationWeek(Object currentGestationWeek) {
		this.currentGestationWeek = currentGestationWeek;
	}

	public Object getCurrentGestationDay() {
		return currentGestationDay;
	}

	public void setCurrentGestationDay(Object currentGestationDay) {
		this.currentGestationDay = currentGestationDay;
	}
	
}
