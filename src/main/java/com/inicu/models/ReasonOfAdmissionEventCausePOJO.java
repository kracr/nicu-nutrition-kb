package com.inicu.models;

public class ReasonOfAdmissionEventCausePOJO {

	String causeValue;
	boolean checkFlag = false;

	public String getCauseValue() {
		return causeValue;
	}

	public void setCauseValue(String causeValue) {
		this.causeValue = causeValue;
	}

	public ReasonOfAdmissionEventCausePOJO(String causeValue) {
		super();
		this.causeValue = causeValue;
	}

	public boolean isCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(boolean checkFlag) {
		this.checkFlag = checkFlag;
	}

	@Override
	public String toString() {
		return "ReasonOfAdmissionEventCausePOJO [causeValue=" + causeValue + ", checkFlag=" + checkFlag + "]";
	}

}
