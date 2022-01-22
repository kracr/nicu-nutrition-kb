package com.inicu.models;

public class UsageAnalytics {
	
	private int totalPatient;
	
	private int patientAdmitted;
	
	private int patientDischarged;

	public int getTotalPatient() {
		return totalPatient;
	}

	public void setTotalPatient(int totalPatient) {
		this.totalPatient = totalPatient;
	}

	public int getPatientAdmitted() {
		return patientAdmitted;
	}

	public void setPatientAdmitted(int patientAdmitted) {
		this.patientAdmitted = patientAdmitted;
	}

	public int getPatientDischarged() {
		return patientDischarged;
	}

	public void setPatientDischarged(int patientDischarged) {
		this.patientDischarged = patientDischarged;
	}

}