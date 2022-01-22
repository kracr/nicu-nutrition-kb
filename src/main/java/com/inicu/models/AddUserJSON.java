package com.inicu.models;

public class AddUserJSON {
	private String firstName;
	private String lastName;
	private String mobileNumber;
	private String emailId;
	private String userName;
	private String password;
	private String status;
	private String roles;
	private String repDoctor;
	private String profImgPath;
	private String signImgPath;
	private String loggeduser;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getRepDoctor() {
		return repDoctor;
	}
	public void setRepDoctor(String repDoctor) {
		this.repDoctor = repDoctor;
	}
	public String getProfImgPath() {
		return profImgPath;
	}
	public void setProfImgPath(String profImgPath) {
		this.profImgPath = profImgPath;
	}
	public String getSignImgPath() {
		return signImgPath;
	}
	public void setSignImgPath(String signImgPath) {
		this.signImgPath = signImgPath;
	}
	public String getLoggedUser() {
		return loggeduser;
	}

	public void setLoggedUser(String loggedUser) {
		this.loggeduser = loggeduser;
	}
	
}
