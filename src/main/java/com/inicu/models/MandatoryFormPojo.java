package com.inicu.models;

import java.sql.Timestamp;
import java.util.Date;

public class MandatoryFormPojo {

	String patientType;
	Float birthWeight;
	String gender;
	String  uhid;

	String dobStr;
	String tobStr;
	String doaStr;
	String toaStr;

	String edd_by;
	String notknowntype;
	String trimesterType;

	Integer gestationWeeks;
	Integer gestationDays;

	private String babyType;
	private String babyNumber;

	private String lmpTimestampStr;
	private String eddTimestampStr;
	private String etTimestampStr;

	private String admissionTime;
	private String babyname;
	private Date admissionDate;

	private Boolean antenatalSteroidGiven;
	private String steroidName;
	private Integer numberofDose;
	private Timestamp givenDate;

	public String getBabyType() {
		return babyType;
	}

	public void setBabyType(String babyType) {
		this.babyType = babyType;
	}

	public String getBabyNumber() {
		return babyNumber;
	}

	public void setBabyNumber(String babyNumber) {
		this.babyNumber = babyNumber;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public Boolean getAntenatalSteroidGiven() {
		return antenatalSteroidGiven;
	}

	public void setAntenatalSteroidGiven(Boolean antenatalSteroidGiven) {
		this.antenatalSteroidGiven = antenatalSteroidGiven;
	}

	public String getSteroidName() {
		return steroidName;
	}

	public void setSteroidName(String steroidName) {
		this.steroidName = steroidName;
	}

	public Integer getNumberofDose() {
		return numberofDose;
	}

	public void setNumberofDose(Integer numberofDose) {
		this.numberofDose = numberofDose;
	}

	public Timestamp getGivenDate() {
		return givenDate;
	}

	public void setGivenDate(Timestamp givenDate) {
		this.givenDate = givenDate;
	}

	public String getBabyname() {
		return babyname;
	}

	public void setBabyname(String babyname) {
		this.babyname = babyname;
	}

	public MandatoryFormPojo() {
		super();
	}

	public String getAdmissionTime() {
		return admissionTime;
	}

	public void setAdmissionTime(String admissionTime) {
		this.admissionTime = admissionTime;
	}

	public Date getAdmissionDate() {
		return admissionDate;
	}

	public void setAdmissionDate(Date admissionDate) {
		this.admissionDate = admissionDate;
	}

	public String getPatientType() {
		return patientType;
	}

	public void setPatientType(String patientType) {
		this.patientType = patientType;
	}

	public Float getBirthWeight() {
		return birthWeight;
	}

	public void setBirthWeight(Float birthWeight) {
		this.birthWeight = birthWeight;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDobStr() {
		return dobStr;
	}

	public void setDobStr(String dobStr) {
		this.dobStr = dobStr;
	}

	public String getTobStr() {
		return tobStr;
	}

	public void setTobStr(String tobStr) {
		this.tobStr = tobStr;
	}

	public String getDoaStr() {
		return doaStr;
	}

	public void setDoaStr(String doaStr) {
		this.doaStr = doaStr;
	}

	public String getToaStr() {
		return toaStr;
	}

	public void setToaStr(String toaStr) {
		this.toaStr = toaStr;
	}

	public String getEdd_by() {
		return edd_by;
	}

	public void setEdd_by(String edd_by) {
		this.edd_by = edd_by;
	}

	public String getNotknowntype() {
		return notknowntype;
	}

	public void setNotknowntype(String notknowntype) {
		this.notknowntype = notknowntype;
	}

	public String getTrimesterType() {
		return trimesterType;
	}

	public void setTrimesterType(String trimesterType) {
		this.trimesterType = trimesterType;
	}

	public Integer getGestationWeeks() {
		return gestationWeeks;
	}

	public void setGestationWeeks(Integer gestationWeeks) {
		this.gestationWeeks = gestationWeeks;
	}

	public Integer getGestationDays() {
		return gestationDays;
	}

	public void setGestationDays(Integer gestationDays) {
		this.gestationDays = gestationDays;
	}

	public String getLmpTimestampStr() {
		return lmpTimestampStr;
	}

	public void setLmpTimestampStr(String lmpTimestampStr) {
		this.lmpTimestampStr = lmpTimestampStr;
	}

	public String getEddTimestampStr() {
		return eddTimestampStr;
	}

	public void setEddTimestampStr(String eddTimestampStr) {
		this.eddTimestampStr = eddTimestampStr;
	}

	public String getEtTimestampStr() {
		return etTimestampStr;
	}

	public void setEtTimestampStr(String etTimestampStr) {
		this.etTimestampStr = etTimestampStr;
	}

	@Override
	public String toString() {
		return "MandatoryFormPojo [patientType=" + patientType
				+ ", birthWeight=" + birthWeight + ", gender=" + gender
				+ ", dobStr=" + dobStr + ", tobStr=" + tobStr + ", doaStr="
				+ doaStr + ", toaStr=" + toaStr + ", edd_by=" + edd_by
				+ ", notknowntype=" + notknowntype + ", trimesterType="
				+ trimesterType + ", gestationWeeks=" + gestationWeeks
				+ ", gestationDays=" + gestationDays + ", lmpTimestampStr="
				+ lmpTimestampStr + ", eddTimestampStr=" + eddTimestampStr
				+ ", etTimestampStr=" + etTimestampStr + "]";
	}

}
