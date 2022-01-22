package com.inicu.postgres.entities;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity	
@Table(name="vwuserinfo")
public class UserInfoView {
	
	@Id
	@Column(name="username")
	private String userName;
	
	@Column(name="companyid")
	private String companyId;
	
	@Column(name="password")
	private String password;
	
	@Column(name="emailaddress")
	private String email;
	
	@Column(name="status", columnDefinition="int2")
	private int active;
	
	@Column(name="hospempname")
	private String name;
	
	@Column(name="designation")
	private String designation;
	
	@Column(name="contactno", columnDefinition="int8")
	private BigInteger mobileNumber;
	
	@Column(name="profilepic")
	private String profilePic;
	
	@Column(name="rolename")
	private String roleName;
	
	@Column(name="signature")
	private String signature;
	
	@Column(name="reportingdoctor")
	private String repDoctor;
	
	private String branchname;
	
	public String getRepDoctor() {
		return repDoctor;
	}

	public void setRepDoctor(String repDoctor) {
		this.repDoctor = repDoctor;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public BigInteger getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(BigInteger mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}
	

}
