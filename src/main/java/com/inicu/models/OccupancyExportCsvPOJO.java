package com.inicu.models;

public class OccupancyExportCsvPOJO {

	private String month;
	private String babyname;
	private String uhid;
	private String dateofbirth;
	private String dateofadmission;
	private String timeofadmission;

	private String inoutstatus;

	// HIS Discharge Date
	private String hisDischargeddate;

	// HIS Discharge Status
	private String hisDischargeStatus;

	private long patientdays;
	private float birthweight;
	private Integer gestationweeks;
	private Integer gestationdays;
	private Integer respSupportDays;

	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getBabyname() {
		return babyname;
	}
	public void setBabyname(String babyname) {
		this.babyname = babyname;
	}
	public String getUhid() {
		return uhid;
	}
	public void setUhid(String uhid) {
		this.uhid = uhid;
	}
	public String getDateofbirth() {
		return dateofbirth;
	}
	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	public String getDateofadmission() {
		return dateofadmission;
	}
	public void setDateofadmission(String dateofadmission) {
		this.dateofadmission = dateofadmission;
	}
	public String getInoutstatus() {
		return inoutstatus;
	}
	public void setInoutstatus(String inoutstatus) {
		this.inoutstatus = inoutstatus;
	}
	public long getPatientdays() {
		return patientdays;
	}
	public void setPatientdays(long patientdays) {
		this.patientdays = patientdays;
	}
	public String getTimeofadmission() {
		return timeofadmission;
	}
	public void setTimeofadmission(String timeofadmission) {
		this.timeofadmission = timeofadmission;
	}
	public float getBirthweight() {
		return birthweight;
	}
	public void setBirthweight(float birthweight) {
		this.birthweight = birthweight;
	}
	public Integer getGestationweeks() {
		return gestationweeks;
	}
	public void setGestationweeks(Integer gestationweeks) {
		this.gestationweeks = gestationweeks;
	}
	public Integer getGestationdays() {
		return gestationdays;
	}
	public void setGestationdays(Integer gestationdays) {
		this.gestationdays = gestationdays;
	}
	public Integer getRespSupportDays() {
		return respSupportDays;
	}
	public void setRespSupportDays(Integer respSupportDays) {
		this.respSupportDays = respSupportDays;
	}

	public String getHisDischargeddate() {
		return hisDischargeddate;
	}

	public void setHisDischargeddate(String hisDischargeddate) {
		this.hisDischargeddate = hisDischargeddate;
	}

	public String getHisDischargeStatus() {
		return hisDischargeStatus;
	}

	public void setHisDischargeStatus(String hisDischargeStatus) {
		this.hisDischargeStatus = hisDischargeStatus;
	}
}
