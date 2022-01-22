package com.inicu.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;


@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientInfoAdmissonFormObj {
	private Object uhid;
	private Object babyName;
	private Object gender;
	
	private Object ipopStatus;
	private Object refferedFrom;
	private Object courseInRefferingHospital;
	private Object modeOfTransportation;
	private Object transportationAlongWith;
	private Object reasonOfReference;
	
	private KeyValueObj nicuRoomNo;
	private KeyValueObj nicuBedNo;
	private KeyValueObj nicuLevel;
	private KeyValueObj criticality;
	
	private String tempDob;
	private Object dateOfBirth;
	private TimeObj timeOfBirth;
	private Object dateOfAdmission;
	
	private Object dayOfLife;
	private Object residentDoctor;
	//reffered from hospital details...
	private PersonalDetailsObj personalDetails;


	public PatientInfoAdmissonFormObj(){
		this.uhid="";
		this.nicuBedNo=new KeyValueObj();
		this.babyName="";
		this.nicuRoomNo=new KeyValueObj();
		this.dateOfAdmission="";
		this.dateOfBirth="";
		this.timeOfBirth = new TimeObj();
		this.ipopStatus="";
		this.nicuLevel=new KeyValueObj();
		this.criticality=new KeyValueObj();
		
		this.refferedFrom="";
		this.courseInRefferingHospital = "";
		this.modeOfTransportation = "";
		this.reasonOfReference = "";
		
		this.personalDetails = new PersonalDetailsObj();
	}


	public Object getUhid() {
		return uhid;
	}


	public void setUhid(Object uhid) {
		this.uhid = uhid;
	}


	public Object getBabyName() {
		return babyName;
	}


	public void setBabyName(Object babyName) {
		this.babyName = babyName;
	}


	public Object getGender() {
		return gender;
	}


	public void setGender(Object gender) {
		this.gender = gender;
	}


	public Object getIpopStatus() {
		return ipopStatus;
	}


	public void setIpopStatus(Object ipopStatus) {
		this.ipopStatus = ipopStatus;
	}


	public Object getRefferedFrom() {
		return refferedFrom;
	}


	public void setRefferedFrom(Object refferedFrom) {
		this.refferedFrom = refferedFrom;
	}


	public Object getCourseInRefferingHospital() {
		return courseInRefferingHospital;
	}


	public void setCourseInRefferingHospital(Object courseInRefferingHospital) {
		this.courseInRefferingHospital = courseInRefferingHospital;
	}


	public Object getModeOfTransportation() {
		return modeOfTransportation;
	}


	public void setModeOfTransportation(Object modeOfTransportation) {
		this.modeOfTransportation = modeOfTransportation;
	}


	public Object getTransportationAlongWith() {
		return transportationAlongWith;
	}


	public void setTransportationAlongWith(Object transportationAlongWith) {
		this.transportationAlongWith = transportationAlongWith;
	}


	public Object getReasonOfReference() {
		return reasonOfReference;
	}


	public void setReasonOfReference(Object reasonOfReference) {
		this.reasonOfReference = reasonOfReference;
	}


	public KeyValueObj getNicuRoomNo() {
		return nicuRoomNo;
	}


	public void setNicuRoomNo(KeyValueObj nicuRoomNo) {
		this.nicuRoomNo = nicuRoomNo;
	}


	public KeyValueObj getNicuBedNo() {
		return nicuBedNo;
	}


	public void setNicuBedNo(KeyValueObj nicuBedNo) {
		this.nicuBedNo = nicuBedNo;
	}


	public KeyValueObj getNicuLevel() {
		return nicuLevel;
	}


	public void setNicuLevel(KeyValueObj nicuLevel) {
		this.nicuLevel = nicuLevel;
	}


	public KeyValueObj getCriticality() {
		return criticality;
	}


	public void setCriticality(KeyValueObj criticality) {
		this.criticality = criticality;
	}


	public Object getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(Object dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public TimeObj getTimeOfBirth() {
		return timeOfBirth;
	}


	public void setTimeOfBirth(TimeObj timeOfBirth) {
		this.timeOfBirth = timeOfBirth;
	}


	public Object getDateOfAdmission() {
		return dateOfAdmission;
	}


	public void setDateOfAdmission(Object dateOfAdmission) {
		this.dateOfAdmission = dateOfAdmission;
	}


	public Object getDayOfLife() {
		return dayOfLife;
	}


	public void setDayOfLife(Object dayOfLife) {
		this.dayOfLife = dayOfLife;
	}


	public Object getResidentDoctor() {
		return residentDoctor;
	}


	public void setResidentDoctor(Object residentDoctor) {
		this.residentDoctor = residentDoctor;
	}


	public PersonalDetailsObj getPersonalDetails() {
		return personalDetails;
	}


	public void setPersonalDetails(PersonalDetailsObj personalDetails) {
		this.personalDetails = personalDetails;
	}
	
	

	public String getTempDob() {
		return tempDob;
	}


	public void setTempDob(String tempDob) {
		this.tempDob = tempDob;
	}


	@Override
	public String toString() {
		return "PatientInfoAdmissonFormObj [uhid=" + uhid + ", babyName=" + babyName + ", gender=" + gender
				+ ", ipopStatus=" + ipopStatus + ", refferedFrom=" + refferedFrom + ", courseInRefferingHospital="
				+ courseInRefferingHospital + ", modeOfTransportation=" + modeOfTransportation
				+ ", transportationAlongWith=" + transportationAlongWith + ", reasonOfReference=" + reasonOfReference
				+ ", nicuRoomNo=" + nicuRoomNo + ", nicuBedNo=" + nicuBedNo + ", nicuLevel=" + nicuLevel
				+ ", criticality=" + criticality + ", dateOfBirth=" + dateOfBirth + ", timeOfBirth=" + timeOfBirth
				+ ", dateOfAdmission=" + dateOfAdmission + ", dayOfLife=" + dayOfLife + ", residentDoctor="
				+ residentDoctor + ", personalDetails=" + personalDetails + "]";
	}


	
	
	



}
