package com.inicu.models;

public class AssessmentInfectionSystemPOJO {
	
	private String uhid;

	private String loggedUser;

	private Object ageAtOnset;

	private InfectionSystemDropDowns dropDowns;

	private InfectionSystemEventPOJO InfectionEventObject;
	
	//not in use anymore
	private String systemStatus;

	public AssessmentInfectionSystemPOJO() {
		super();
		// TODO Auto-generated constructor stub
		this.dropDowns = new InfectionSystemDropDowns();
		this.InfectionEventObject = new InfectionSystemEventPOJO();
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}

	public Object getAgeAtOnset() {
		return ageAtOnset;
	}

	public void setAgeAtOnset(Object ageAtOnset) {
		this.ageAtOnset = ageAtOnset;
	}
	
	public String getSystemStatus() {
		return systemStatus;
	}

	public void setSystemStatus(String systemStatus) {
		this.systemStatus = systemStatus;
	}
	

	public InfectionSystemDropDowns getDropDowns() {
		return dropDowns;
	}

	public void setDropDowns(InfectionSystemDropDowns dropDowns) {
		this.dropDowns = dropDowns;
	}

	public InfectionSystemEventPOJO getInfectionEventObject() {
		return InfectionEventObject;
	}

	public void setInfectionEventObject(InfectionSystemEventPOJO infectionEventObject) {
		InfectionEventObject = infectionEventObject;
	}
}
