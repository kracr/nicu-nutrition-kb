package com.inicu.postgres.entities;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity	
@Table(name="inicuuser")
public class User {

	@Id
	@Column(name="id",columnDefinition="bigserial") 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private BigInteger id;

	@Column(name="creationtime")
	private Timestamp creationTime;

	@Column(name="modificationtime")
	private Timestamp modTime;

	@Column(name="username")
	private String userName;

	@Column(name="companyid")
	private String companyId;

	@Column(name="password")
	private String password;

	@Column(name="emailaddress")
	private String email;

	@Column(name="comments")
	private String comments;

	@Column(name="active",columnDefinition = "int2")
	private int active;

	@Column(name="deleted")
	private String deleted;

	@Column(name="inactivedate")
	private Date inactiveDate;

	@Column(name="firstname")
	private String firstName;

	@Column(name="lastname")
	private String lastName;

	@Column(name="designation")
	private String designation;

	@Column(name="mobile", columnDefinition="int8")
	private BigInteger mobileNumber; 

	@Column(name="picture")
	private String imgPath;

	@Column(name="digitalsign")
	private String signPath;

	@Column(name="reportingdoctor")
	private String repDoctor;
	
	@Column(name="loggeduser")
	private String loggeduser;
	
	@Column(name="branchname")
	private String branchName;

	@Transient
	private String branchType;
	
	@Transient
	private String userRole;

	public String getBranchType() {
		return branchType;
	}

	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}

	public String getRepDoctor() {
		return repDoctor;
	}

	public void setRepDoctor(String repDoctor) {
		this.repDoctor = repDoctor;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Timestamp getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	public Timestamp getModTime() {
		return modTime;
	}

	public void setModTime(Timestamp modTime) {
		this.modTime = modTime;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public Date getInactiveDate() {
		return inactiveDate;
	}

	public void setInactiveDate(Date inactiveDate) {
		this.inactiveDate = inactiveDate;
	}

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

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getSignPath() {
		return signPath;
	}

	public void setSignPath(String signPath) {
		this.signPath = signPath;
	}
	public String getLoggedUser() {
		return this.loggeduser;
	}

	public void setLoggedUser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}


	
}
