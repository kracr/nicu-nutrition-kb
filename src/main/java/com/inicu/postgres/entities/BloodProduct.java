package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the blood_products database table.
 * 
 */
@Entity
@Table(name="blood_products")
@NamedQuery(name="BloodProduct.findAll", query="SELECT b FROM BloodProduct b")
public class BloodProduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long bloodproductsid;

	private String bloodgroup;

	private String bloodproduct;

	private Timestamp creationtime;

	private String dose;

	private String duration;

	private String loggeduser;

	private Timestamp modificationtime;

	private String uhid;
	
	private String babyfeedid;
	
	@Column(columnDefinition="bool")
	 private Boolean isBloodGiven;
	
	
	

	public Boolean getIsBloodGiven() {
		return isBloodGiven;
	}

	public void setIsBloodGiven(Boolean isBloodGiven) {
		this.isBloodGiven = isBloodGiven;
	}

	public Long getBloodproductsid() {
		return this.bloodproductsid;
	}

	public void setBloodproductsid(Long bloodproductsid) {
		this.bloodproductsid = bloodproductsid;
	}

	public String getBloodgroup() {
		return this.bloodgroup;
	}

	public void setBloodgroup(String bloodgroup) {
		this.bloodgroup = bloodgroup;
	}

	public String getBloodproduct() {
		return this.bloodproduct;
	}

	public void setBloodproduct(String bloodproduct) {
		this.bloodproduct = bloodproduct;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getDose() {
		return this.dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	public String getDuration() {
		return this.duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getLoggeduser() {
		return this.loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}


	public String getBabyfeedid() {
		return babyfeedid;
	}

	public void setBabyfeedid(String babyfeedid) {
		this.babyfeedid = babyfeedid;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public BloodProduct() {
		super();
		this.bloodproductsid = bloodproductsid;
		this.bloodgroup = bloodgroup;
		this.bloodproduct = bloodproduct;
		this.creationtime = creationtime;
		this.dose = dose;
		this.duration = duration;
		this.loggeduser = loggeduser;
		this.modificationtime = modificationtime;
		this.uhid = uhid;
		this.babyfeedid = babyfeedid;
	}


	
}