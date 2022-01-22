package com.inicu.models;

public class PersonalDetailsObj {

	private Object mothersName;
	private Object fathersName;
	private Object mobileNumber;
	private Object landlineNumber;
	private Object aadharCardNumber;
	private Object emailId;
	private Object address;
	private Object village;
	
	//in case of emergency details....
	private Object emergencyContactName;
	private Object emergencyContactNo;
	private Object relationship;
	
	
	
	public PersonalDetailsObj (){
		this.mothersName = "";
		this.fathersName ="";
		this.mobileNumber = "";
		this.landlineNumber ="";
		this.aadharCardNumber = "";
		this.emailId ="";
		this.address = "";
		this.village = "";
		this.emergencyContactName ="";
		this.emergencyContactNo ="";
		this.relationship = "";
		
		
	}
	public Object getMothersName() {
		return mothersName;
	}
	public void setMothersName(Object mothersName) {
		this.mothersName = mothersName;
	}
	public Object getFathersName() {
		return fathersName;
	}
	public void setFathersName(Object fathersName) {
		this.fathersName = fathersName;
	}
	public Object getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(Object mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public Object getLandlineNumber() {
		return landlineNumber;
	}
	public void setLandlineNumber(Object landlineNumber) {
		this.landlineNumber = landlineNumber;
	}
	public Object getAadharCardNumber() {
		return aadharCardNumber;
	}
	public void setAadharCardNumber(Object aadharCardNumber) {
		this.aadharCardNumber = aadharCardNumber;
	}
	public Object getEmailId() {
		return emailId;
	}
	public void setEmailId(Object emailId) {
		this.emailId = emailId;
	}
	public Object getAddress() {
		return address;
	}
	public void setAddress(Object address) {
		this.address = address;
	}
	public Object getVillage() {
		return village;
	}
	public void setVillage(Object village) {
		this.village = village;
	}
	public Object getEmergencyContactName() {
		return emergencyContactName;
	}
	public void setEmergencyContactName(Object emergencyContactName) {
		this.emergencyContactName = emergencyContactName;
	}
	public Object getEmergencyContactNo() {
		return emergencyContactNo;
	}
	public void setEmergencyContactNo(Object emergencyContactNo) {
		this.emergencyContactNo = emergencyContactNo;
	}
	public Object getRelationship() {
		return relationship;
	}
	public void setRelationship(Object relationship) {
		this.relationship = relationship;
	}
	



	
}
