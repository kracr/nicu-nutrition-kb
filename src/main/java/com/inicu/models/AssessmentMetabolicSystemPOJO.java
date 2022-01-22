package com.inicu.models;

public class AssessmentMetabolicSystemPOJO {

	private String uhid;

	private String loggedUser;

	private String systemStatus;

	private Object ageAtOnset;

	private MetabolicSystemDropDowns dropDowns;

	private MetabolicSystemEventPOJO metabolicEventObject;

	public AssessmentMetabolicSystemPOJO() {
		super();
		this.dropDowns = new MetabolicSystemDropDowns();
		this.metabolicEventObject = new MetabolicSystemEventPOJO();

	}

	public String getUhid() {
		return uhid;
	}

	public String getLoggedUser() {
		return loggedUser;
	}

	public String getSystemStatus() {
		return systemStatus;
	}

	public Object getAgeAtOnset() {
		return ageAtOnset;
	}

	public MetabolicSystemDropDowns getDropDowns() {
		return dropDowns;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}

	public void setSystemStatus(String systemStatus) {
		this.systemStatus = systemStatus;
	}

	public void setAgeAtOnset(Object ageAtOnset) {
		this.ageAtOnset = ageAtOnset;
	}

	public void setDropDowns(MetabolicSystemDropDowns dropDowns) {
		this.dropDowns = dropDowns;
	}

	public MetabolicSystemEventPOJO getMetabolicEventObject() {
		return metabolicEventObject;
	}

	public void setMetabolicEventObject(MetabolicSystemEventPOJO metabolicEventObject) {
		this.metabolicEventObject = metabolicEventObject;
	}

}
