package com.inicu.models;

import java.sql.Timestamp;

/**
 * @author inicu
 *
 */
public class QISepsisPatientData {

	String uhid;
	Timestamp firstAssessment;
	String ageAtOnset;

	String statusAssessment;
	int durationIndays;

	String medications;

	String sepsisScreen;
	String cultureReport;

	public QISepsisPatientData() {
		super();
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getStatusAssessment() {
		return statusAssessment;
	}

	public void setStatusAssessment(String statusAssessment) {
		this.statusAssessment = statusAssessment;
	}

	public Timestamp getFirstAssessment() {
		return firstAssessment;
	}

	public void setFirstAssessment(Timestamp firstAssessment) {
		this.firstAssessment = firstAssessment;
	}

	public String getAgeAtOnset() {
		return ageAtOnset;
	}

	public void setAgeAtOnset(String ageAtOnset) {
		this.ageAtOnset = ageAtOnset;
	}

	public int getDurationIndays() {
		return durationIndays;
	}

	public void setDurationIndays(int durationIndays) {
		this.durationIndays = durationIndays;
	}

	public String getMedications() {
		return medications;
	}

	public void setMedications(String medications) {
		this.medications = medications;
	}

	public String getSepsisScreen() {
		return sepsisScreen;
	}

	public void setSepsisScreen(String sepsisScreen) {
		this.sepsisScreen = sepsisScreen;
	}

	public String getCultureReport() {
		return cultureReport;
	}

	public void setCultureReport(String cultureReport) {
		this.cultureReport = cultureReport;
	}

	@Override
	public String toString() {
		return "QISepsisPatientData [uhid=" + uhid + ", statusAssessment=" + statusAssessment + ", firstAssessment="
				+ firstAssessment + ", ageAtOnset=" + ageAtOnset + ", durationIndays=" + durationIndays
				+ ", medications=" + medications + ", sepsisScreen=" + sepsisScreen + ", cultureReport=" + cultureReport
				+ "]";
	}

}
