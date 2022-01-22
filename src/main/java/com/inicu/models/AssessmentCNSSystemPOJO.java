package com.inicu.models;

public class AssessmentCNSSystemPOJO {

	private String uhid;

	private String loggedUser;

	private Object ageAtOnset;

	private CNSSystemDropDowns dropDowns;

	private CNSSystemEventPOJO CNSEventObject;

	private String workingWeight;
	
	private String orderSelectedText;
	
	//Not in use anymore
	private String systemStatus;

	public AssessmentCNSSystemPOJO() {
		super();
		this.dropDowns = new CNSSystemDropDowns();
		this.CNSEventObject = new CNSSystemEventPOJO();
	}
	
	public String getSystemStatus() {
		return systemStatus;
	}

	public void setSystemStatus(String systemStatus) {
		this.systemStatus = systemStatus;
	}
	
	public String getUhid() {
		return uhid;
	}

	public String getLoggedUser() {
		return loggedUser;
	}

	public Object getAgeAtOnset() {
		return ageAtOnset;
	}

	public CNSSystemDropDowns getDropDowns() {
		return dropDowns;
	}

	public CNSSystemEventPOJO getCNSEventObject() {
		return CNSEventObject;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}

	public void setAgeAtOnset(Object ageAtOnset) {
		this.ageAtOnset = ageAtOnset;
	}

	public void setDropDowns(CNSSystemDropDowns dropDowns) {
		this.dropDowns = dropDowns;
	}

	public void setCNSEventObject(CNSSystemEventPOJO cNSEventObject) {
		CNSEventObject = cNSEventObject;
	}

	public String getWorkingWeight() {
		return workingWeight;
	}

	public void setWorkingWeight(String workingWeight) {
		this.workingWeight = workingWeight;
	}

	public String getOrderSelectedText() {
		return orderSelectedText;
	}

	public void setOrderSelectedText(String orderSelectedText) {
		this.orderSelectedText = orderSelectedText;
	}

}
